package com.example.myTest.Activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bravin.btoast.BToast;
import com.czt.mp3recorder.MP3Recorder;
import com.example.myTest.R;
import com.example.myTest.Utils.Audio.AudioManager;
import com.example.myTest.Utils.Audio.MediaManager;
import com.example.myTest.Utils.QiniuToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.qiniu.android.common.AutoZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

import static com.example.myTest.Utils.File.FileUtils.byteToFile;
import static com.example.myTest.Utils.QiniuToken.tokenupload;

public class AudioActivity extends Activity implements View.OnClickListener, AudioManager.AudioStateListener {

    private String TAG = "AudioActivity";

    AudioManager manager;
    private static Activity activity;
    private MP3Recorder mRecorder;
    private String fileName;
    private String filePath;

    private String QiniuTokenHttp = "http://img-qn.rhxzhj.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_audio_test);
        activity = this;
        init();

    }

    private void init() {
        int[] ids = new int[]{
                R.id.mp3_down,
                R.id.mp3_play,
                R.id.start,
                R.id.mp3_over,
                R.id.to_mp3,
                R.id.mp3_up,
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
            case R.id.mp3_play:

                MediaManager.playSound(this, filePath + fileName, new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(AudioActivity.this, "play........mp3.........over", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.mp3_down:
//                下载

                AsyncHttpClient httpClient = new AsyncHttpClient();
                httpClient.setURLEncodingEnabled(false);

                String[] allowedContentTypes = new String[]{"audio/mpeg"};
//                String[] allowedContentTypes = new String[] {"application/vnd.android.package-archive"};
                httpClient.get(QiniuTokenHttp + "/" + fileName, new BinaryHttpResponseHandler(allowedContentTypes) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                        String tempPath = filePath + "activity_main_test" + fileName;
                        Log.d(TAG, "onSuccess: " + binaryData.toString());
                        byteToFile(binaryData, tempPath);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                        Log.d(TAG, "onFailure: --------->" + statusCode + error);
                    }
                });


                break;
            case R.id.mp3_up:
                //                上传
                try {
//                    String AccessKey = "yV-7Mf7qwqHNDAlYNTVmaI9XzcOI63BHK8bhhEBi";//  AK
//                    String SecretKey = "-JqewMs7OnvdMRjpNAdqCbhDMY8KvqbWSFfnQtfu";//SK


//                    String AccessKey = "1v0flI4KTNjPAFlCMl8_JeIgHfJtfIFVzKAGCidC";//  AK  renhe
//                    String SecretKey = "YSpnaCpayLY-0Snc28yO_x516Jssi9zuUliEN4Pa";//SK


                    long time = (System.currentTimeMillis() / 1000) + 60 * 60;

                    Toast.makeText(AudioActivity.this, time + "", Toast.LENGTH_SHORT).show();

                    String scope = "rhwytzg" + ":" + fileName;
                    String deadline = String.valueOf(time);

//                    String key = manager.getFileName();

                    Configuration config = new Configuration.Builder()
                            .zone(AutoZone.autoZone)
                            .build();
                    UploadManager uploadManager = new UploadManager(config);

                    Log.d(TAG, "onClick: " + filePath + fileName);

                    uploadManager.put(filePath + fileName, fileName, tokenupload(scope, deadline),
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject res) {
                                    Log.d(TAG, "complete: " + info.toString());
                                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                                    if (info.isOK()) {
                                        Log.d(TAG, "Upload Success");
                                    } else {
                                        Log.d(TAG, "Upload Fail");
                                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                    }
                                    Log.d(TAG, key + ",\r\n " + info + ",\r\n " + res);
                                }
                            }, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.to_mp3:
                File file = new File(getFilesDir() + "/Recode/");
                if (!file.exists()) {
                    file.mkdir();
                }
                fileName = System.currentTimeMillis() + ".mp3";
                filePath = getFilesDir() + "/Recode/";

                mRecorder = new MP3Recorder(new File(filePath + fileName));
                Log.d(TAG, "onClick: " + filePath + fileName);
                try {
                    mRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.mp3_over:

                mRecorder.stop();
                BToast.success(this)
                        .text("over")
                        .show();
                break;
            case R.id.prepare:
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

                    Log.d(TAG, "onClick: " + manager.getCurrentPath() + "=====" + key);

                    uploadManager.put(manager.getCurrentPath(), key, tokenupload(scope, deadline),
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject res) {
                                    Log.d(TAG, "complete: " + info.toString());
                                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                                    if (info.isOK()) {
                                        Log.d(TAG, "Upload Success");
                                    } else {
                                        Log.d(TAG, "Upload Fail");
                                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                    }
                                    Log.d(TAG, key + ",\r\n " + info + ",\r\n " + res);
                                }
                            }, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.download:
//                下载

                AsyncHttpClient httpClient1 = new AsyncHttpClient();
                httpClient1.setURLEncodingEnabled(false);
                Log.d("activity_main_test", "onClick: " + QiniuTokenHttp + "/" + manager.getFileName());
                String[] allowedContentTypes1 = new String[]{"audio/amr"};
//                String[] allowedContentTypes = new String[] {"application/vnd.android.package-archive"};
                httpClient1.get(QiniuTokenHttp + "/" + manager.getFileName(), new BinaryHttpResponseHandler(allowedContentTypes1) {
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
