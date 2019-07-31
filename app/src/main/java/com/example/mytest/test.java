package com.example.mytest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.mytest.Listener.PermissionListener;
import com.example.mytest.Utils.AudioManager;
import com.example.mytest.Utils.MediaManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.qiniu.android.common.AutoZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;

import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.test.my.R;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.example.mytest.Utils.FileUtils.byteToFile;
import static com.example.mytest.Utils.QiniuToken.tokenupload;

public class test extends Activity implements View.OnClickListener, AudioManager.AudioStateListener {
    AudioManager manager;
    private static PermissionListener mListener;
    private static Activity activity;
    private Button test;
    private TextView textView;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        activity = this;

        initView();
        initData();


//        byte[] sk = StringUtils.utf8Bytes("YSpnaCpayLY-0Snc28yO_x516Jssi9zuUliEN4Pa");
//        SecretKeySpec secretKey = new SecretKeySpec(sk, "HmacSHA1");
//        Mac mac;
//        try {
//            mac = javax.crypto.Mac.getInstance("HmacSHA1");
//            mac.init(secretKey);
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//            throw new IllegalArgumentException(e);
//        }
//        String encodedSign = UrlSafeBase64.encodeToString(mac.doFinal(object.toString().getBytes()));

    }

    private void initView() {
        int[] ids = new int[]{R.id.time_picker, R.id.start, R.id.over, R.id.play, R.id.date, R.id.delete, R.id.Permissions, R.id.phone_number, R.id.delete_phone_number, R.id.date, R.id.prepare, R.id.download, R.id.download_play};
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
        textView = findViewById(R.id.textView);
//        test = findViewById(R.id.test);
//        test.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        test.setText("11111");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        test.setText("22222");
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
    }

    private void initData() {

    }

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_CONTACTS",
            "android.permission.WRITE_CONTACTS",
            "android.permission.GET_ACCOUNTS",
            "android.permission.CAMERA",
            "android.permission.CHANGE_WIFI_STATE",
            "android.permission.RECORD_AUDIO",
            "android.permission.INTERNET",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"};

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prepare:

                //        Log.d("test", "onCreate: " + this.getFilesDir() + "----------------" + this.fileList().toString());
                String s = this.getFilesDir() + "/" + "recode";
                File dir = new File(s);
                if (!dir.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    dir.mkdirs();
                }
                manager = AudioManager.getInstance(s);
                manager.setOnAudioStateListener(this);
                Toast.makeText(this, "prepare", Toast.LENGTH_SHORT).show();
                break;
            case R.id.start:
                Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
                manager.prepareAudio();
                break;
            case R.id.over:
                Toast.makeText(this, "over", Toast.LENGTH_SHORT).show();
                manager.release();
                break;
            case R.id.play:
                Toast.makeText(this, "play", Toast.LENGTH_SHORT).show();
                MediaManager.playSound(this, manager.getCurrentPath(), new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(test.this, "play.................over", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.delete:
                Toast.makeText(test.this, "delete.................???", Toast.LENGTH_SHORT).show();
                manager.cancel();
                break;
            case R.id.Permissions:
                if (Build.VERSION.SDK_INT >= 23) {//判断当前系统是不是Android6.0
                    requestRuntimePermissions(PERMISSIONS_STORAGE, new PermissionListener() {
                        @Override
                        public void granted() {
                            //权限申请通过
                        }

                        @Override
                        public void denied(List<String> deniedList) {
                            //权限申请未通过
//                            for (String denied : deniedList) {
//                                if (denied.equals("android.permission.ACCESS_FINE_LOCATION")) {
//                                    Toast.makeText(test.this, "start", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(test.this, "start", Toast.LENGTH_SHORT).show();
//                                }
//                            }
                            Log.d("test", "denied: " + deniedList.toString());
                        }
                    });
                }
                break;
            case R.id.phone_number:
                addContactPhoneNumber("我自己啊", new String[]{"15527017729", "123123123", "321321321"});
                break;
            case R.id.delete_phone_number:
                try {
                    testDelete();
                    Toast.makeText(test.this, "delete.................ok", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.date:

//                上传
                try {
//                    String AccessKey = "yV-7Mf7qwqHNDAlYNTVmaI9XzcOI63BHK8bhhEBi";//  AK
//                    String SecretKey = "-JqewMs7OnvdMRjpNAdqCbhDMY8KvqbWSFfnQtfu";//SK


//                    String AccessKey = "1v0flI4KTNjPAFlCMl8_JeIgHfJtfIFVzKAGCidC";//  AK  renhe
//                    String SecretKey = "YSpnaCpayLY-0Snc28yO_x516Jssi9zuUliEN4Pa";//SK


                    long time = (System.currentTimeMillis() / 1000) + 60 * 60;

                    Toast.makeText(test.this, time + "", Toast.LENGTH_SHORT).show();

                    String scope = "rhwytzg" + ":" + manager.getFileName();
                    String deadline = String.valueOf(time);

                    String key = manager.getFileName();
                    Configuration config = new Configuration.Builder()
                            .zone(AutoZone.autoZone)
                            .build();
                    UploadManager uploadManager = new UploadManager(config);
                    uploadManager.put(manager.getCurrentPath(), key, tokenupload(scope, deadline),
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject res) {
                                    Log.d("test?????", "complete: " + info.toString());
                                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                                    if (info.isOK()) {
                                        Log.d("testsdadada", "Upload Success");
                                    } else {
                                        Log.d("testasasdadasd", "Upload Fail");
                                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                    }
                                    Log.d("test", key + ",\r\n " + info + ",\r\n " + res);
                                }
                            }, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.download:
//                下载
                String http = "http://img-qn.rhxzhj.com";
                AsyncHttpClient httpClient = new AsyncHttpClient();
                httpClient.setURLEncodingEnabled(false);
                Log.d("test", "onClick: " + http + "/" + manager.getFileName());
                String[] allowedContentTypes = new String[]{"audio/amr"};
//                String[] allowedContentTypes = new String[] {"application/vnd.android.package-archive"};
                httpClient.get(http + "/" + manager.getFileName(), new BinaryHttpResponseHandler(allowedContentTypes) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                        String tempPath = test.getContext().getFilesDir() + "/" + "recode" + "/" + "test" + manager.getFileName();
                        Log.d("test", "onSuccess: " + binaryData.toString());
                        byteToFile(binaryData, tempPath);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                        Log.d("test", "onFailure: --------->" + statusCode + error);
                    }
                });
                break;
            case R.id.download_play:
                Toast.makeText(this, "download_paly", Toast.LENGTH_SHORT).show();
                MediaManager.playSound(this, test.getContext().getFilesDir() + "/" + "recode" + "/" + "test" + manager.getFileName(), new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(test.this, "play.................over", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.time_picker:
                showPickTime();
                break;
        }
    }

    private void showPickTime() {
        TimePickerView timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH点mm分");
                textView.setText(formatter.format(date));
//                Log.d("test", "onTimeSelect: " + formatter.format(date));
            }
        }).setTitleText("上门时间")
                .setType(new boolean[]{true, true, true, true, true, false})
                .setBgColor(Color.rgb(255, 255, 255))
                .setTitleBgColor(Color.rgb(255, 255, 255)).build();
        timePickerView.show();
    }

    /**
     * 申请权限
     */
    public static void requestRuntimePermissions(
            String[] permissions, PermissionListener listener) {
        mListener = listener;
        List<String> permissionList = new ArrayList<>();
        // 遍历每一个申请的权限，把没有通过的权限放在集合中
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            } else {
                mListener.granted();
            }
        }
        // 申请权限
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    permissionList.toArray(new String[permissionList.size()]), 1);
        }
    }

    /**
     * 申请后的处理
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            List<String> deniedList = new ArrayList<>();
            // 遍历所有申请的权限，把被拒绝的权限放入集合
            for (int i = 0; i < grantResults.length; i++) {
                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    mListener.granted();
                } else {
                    deniedList.add(permissions[i]);
                }
            }
            if (!deniedList.isEmpty()) {
                mListener.denied(deniedList);
            }
        }
    }


    @Override
    public void wellPrepared() {
        Toast.makeText(this, "well", Toast.LENGTH_SHORT).show();
    }

    private void addContactPhoneNumber(String name, String[] number) {
        Toast.makeText(this, "success?", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            //创建一个空的ContentValues
            ContentValues values = new ContentValues();
            //首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
            Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
            long rawContactId = ContentUris.parseId(rawContactUri);
            //往data表插入姓名数据
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);//内容类型
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);//设置联系人名字
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);//向联系人URI添加联系人名字
            //往data表插入电话数据
            for (int i = 0; i < number.length; i++) {
                values.clear();
                values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number[i]);
                values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);//插入手机号码
                getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);


            }
        }
    }

    private void testDelete() throws Exception {
        String name = "我自己啊";
        //根据姓名求id
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID}, "display_name=?", new String[]{name}, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            //根据id删除data中的相应数据
            resolver.delete(uri, "display_name=?", new String[]{name});
            uri = Uri.parse("content://com.android.contacts/data");
            resolver.delete(uri, "raw_contact_id=?", new String[]{id + ""});
        }
    }
}
