package com.example.myTest.Activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myTest.Utils.Audio.AudioManager;
import com.example.myTest.Utils.Audio.MediaManager;
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

import cz.msebera.android.httpclient.Header;

import static com.example.myTest.Utils.File.FileUtils.byteToFile;
import static com.example.myTest.Utils.QiniuToken.tokenupload;

public class AudioActivity extends Activity implements View.OnClickListener, AudioManager.AudioStateListener {

    AudioManager manager;
    private static Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_audio_test);
        activity = this;
        init();
    }

    private void init() {
        int[] ids = new int[]{R.id.start,
                R.id.over,
                R.id.play,
                R.id.date,
                R.id.delete,
                R.id.date,
                R.id.prepare,
                R.id.download,
                R.id.download_play};
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prepare:
                //        Log.d("activity_main_test", "onCreate: " + this.getFilesDir() + "----------------" + this.fileList().toString());
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
                        Toast.makeText(AudioActivity.this, "play.................over", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.delete:
                Toast.makeText(AudioActivity.this, "delete.................???", Toast.LENGTH_SHORT).show();
                manager.cancel();
                break;

            case R.id.date:

//                上传
                try {
//                    String AccessKey = "yV-7Mf7qwqHNDAlYNTVmaI9XzcOI63BHK8bhhEBi";//  AK
//                    String SecretKey = "-JqewMs7OnvdMRjpNAdqCbhDMY8KvqbWSFfnQtfu";//SK


//                    String AccessKey = "1v0flI4KTNjPAFlCMl8_JeIgHfJtfIFVzKAGCidC";//  AK  renhe
//                    String SecretKey = "YSpnaCpayLY-0Snc28yO_x516Jssi9zuUliEN4Pa";//SK


                    long time = (System.currentTimeMillis() / 1000) + 60 * 60;

                    Toast.makeText(AudioActivity.this, time + "", Toast.LENGTH_SHORT).show();

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
                                        Log.d("test", "Upload Success");
                                    } else {
                                        Log.d("test", "Upload Fail");
                                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                    }
                                    Log.d("activity_main_test", key + ",\r\n " + info + ",\r\n " + res);
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
                Log.d("activity_main_test", "onClick: " + http + "/" + manager.getFileName());
                String[] allowedContentTypes = new String[]{"audio/amr"};
//                String[] allowedContentTypes = new String[] {"application/vnd.android.package-archive"};
                httpClient.get(http + "/" + manager.getFileName(), new BinaryHttpResponseHandler(allowedContentTypes) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                        String tempPath = getFilesDir() + "/" + "recode" + "/" + "activity_main_test" + manager.getFileName();
                        Log.d("activity_main_test", "onSuccess: " + binaryData.toString());
                        byteToFile(binaryData, tempPath);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                        Log.d("activity_main_test", "onFailure: --------->" + statusCode + error);
                    }
                });
                break;
            case R.id.download_play:
                Toast.makeText(this, "download_paly", Toast.LENGTH_SHORT).show();
                MediaManager.playSound(this, getFilesDir() + "/" + "recode" + "/" + "activity_main_test" + manager.getFileName(), new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(AudioActivity.this, "play.................over", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    public void wellPrepared() {
        Toast.makeText(this, "well", Toast.LENGTH_SHORT).show();
    }

}
