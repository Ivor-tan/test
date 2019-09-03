package com.example.myTest.Application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.bravin.btoast.BToast;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import java.io.File;


public class CustomApplication extends Application {
    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        initImageLoader(context);//图片加载框架

        initBToast();
    }

    private void initBToast() {
        BToast.Config.getInstance()
//                .setAnimate() // Whether to startAnimation. default is fasle;
//                .setAnimationDuration()// Animation duration. default is 800 millisecond
//                .setAnimationGravity()// Animation entering position. default is BToast.ANIMATION_GRAVITY_TOP
//                .setDuration()// toast duration  is Either BToast.DURATION_SHORT or BToast.DURATION_LONG
//                .setTextColor()// textcolor. default is white
//                .setErrorColor()// error style background Color default is red
//                .setInfoColor()// info style background Color default is blue
//                .setSuccessColor(R.color.blue1)// success style background Color default is green
//                .setWarningColor()// waring style background Color default is orange
//                .setLayoutGravity()// whan show an toast with target, coder can assgin position relative to target. default is BToast.LAYOUT_GRAVITY_BOTTOM
//                .setLongDurationMillis()// long duration. default is 4500 millisecond
//                .setRadius()// radius. default is half of view's height. coder can assgin a positive value
//                .setRelativeGravity()// whan show an toast with target, coder can assgin position relative to toastself(like relativeLayout start end center), default is BToast.RELATIVE_GRAVITY_CENTER
//                .setSameLength()// sameLength.  whan layoutGravity is BToast.LAYOUT_GRAVITY_TOP or BToast.LAYOUT_GRAVITY_BOTTOM,sameLength mean toast's width is as same as target,otherwise is same height
//                .setShortDurationMillis()// short duration. default is 3000 millisecond
//                .setShowIcon()// show or hide icon
//                .setTextSize()// text size. sp unit
                .apply(this);// must call

    }

    /**
     * 获取设备id
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if ("".equals(deviceId)) {
            deviceId = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    /**
     * 初始化ImageLoader
     */
    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, getAppTempDir());// 获取到缓存的目录地址

        // 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                // max width, max height，即保存的每个缓存文件的最大长宽
                //				.memoryCacheExtraOptions(480, 800)
                // 线程池内加载的数量
                .threadPoolSize(3) // 线程优先级
                .threadPriority(Thread.NORM_PRIORITY - 2) //内存缓存2M
                .memoryCacheSize(2 * 1024 * 1024) //硬盘缓存30MB
                .diskCacheSize(30 * 1024 * 1024) //将保存的时候的URI名称用MD5
                //				.diskCacheFileNameGenerator(new Md5FileNameGenerator()) // 加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())//将保存的时候的URI名称用HASHCODE加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(100) //缓存的File数量
                .diskCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
                //                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                //				.writeDebugLogs() // Remove for release app 开启debug模式，打印日志
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }

    /**
     * 获取app保存图片的临时路径
     *
     * @return
     */
    public static String getAppTempDir() {

        File file = new File(getAppRootDir() + File.separator + "image_cache");
        if (!file.exists()) {
            file.mkdirs();
        }

        return file.getAbsolutePath();
    }

    /**
     * 获取app的根目录
     *
     * @return
     */
    public static String getAppRootDir() {

        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cachePath = CustomApplication.context.getExternalCacheDir().getPath();
        } else {
            cachePath = CustomApplication.context.getCacheDir().getPath();
        }

        File temp = new File(cachePath);
        if (!temp.exists()) {
            temp.mkdirs();
        }

        return temp.getAbsolutePath();
    }
}
