package com.example.myTest.Utils.Http;

import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class okHttp3_RequestUtil {

    public RequestInter requestInter;

    public interface RequestInter {
        void onSuccess(String response);

        void onFail(int code, String estring);
    }

    /**
     * @param TAG          日志TAG
     * @param url          请求URL
     * @param map          请求参数
     * @param requestInter 回调方法 new一个就行
     */
    public static void request(String TAG, String url, Map<String, ? extends Object> map, RequestInter requestInter) {

        Log.d(TAG, "请求URL " + url);
        Log.d(TAG, "请求参数 " + new Gson().toJson(map));
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                    OkHttpClient client = new OkHttpClient();
                    Looper.prepare();


                    Request request = new Request.Builder()
                            .url(url)//请求接口
                            .post(RequestBody.create(mediaType, new Gson().toJson(map)))
                            .build();

                    response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        requestInter.onSuccess(response.body().string());

                    } else {
                        requestInter.onFail(response.code(), response.message());
                    }
                    Looper.loop();
                } catch (Exception e) {

                    requestInter.onFail(0, e.toString());
                }

            }
        }).start();

    }

}
