package com.example.myTest.Utils.DownLoadManager;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class DownLoadManager {
    //Log标记
    private static final String TAG = "test";
    //APK文件类型
    private static String APK_CONTENTTYPE = "application/vnd.android.package-archive";
    //PNG文件类型
    private static String PNG_CONTENTTYPE = "image/png";
    //JPG文件类型
    private static String JPG_CONTENTTYPE = "image/jpg";
    //文件后缀名
    private static String fileSuffix = "";


    /**
     * 写入文件到本地
     *
     * @param name
     * @param body
     * @return
     */
    public static boolean writeResponseBodyToDisk(String name, ResponseBody body, ProgressListener progressListener) {

        //下载文件类型判断，并对fileSuffix赋值
        String type = body.contentType().toString();

        if (type.equals(APK_CONTENTTYPE)) {
            fileSuffix = ".apk";
        } else if (type.equals(PNG_CONTENTTYPE)) {
            fileSuffix = ".png";
        }
        // 其他类型同上 需要的判断自己加入.....

        //下面就是一顿写入，文件写入的位置是通过参数file来传递的
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
        if (!path.exists()) {
            path.mkdir();
        }

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + name);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            is = body.byteStream();
            long total = body.contentLength();

            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                int progress = (int) (sum * 1.0f / total * 100);
                progressListener.onProgress(progress);
            }
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (is != null)
                    is.close();

                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }
    }


    public interface ProgressListener {
        void onProgress(int progress);
    }
}