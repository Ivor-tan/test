package com.example.netutileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestLoader requestLoader = new RequestLoader("");
        Map<String, String> map = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map.put("activityId", "6");
        map.put("content", "11111");
        map.put("photoPath", "");
        map.put("ownerId", "4327");
        map.put("vallagePropertyId", "3488");
        map1.put("biz",new Gson().toJson(map));

        Observable<Object> objectObservable = requestLoader.mMovieService.get(map1);

        requestLoader.toSubscribe(objectObservable, new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {




            }

            @Override
            public void onError(Throwable e) {


            }

            @Override
            public void onComplete() {

            }
        });
//        objectObservable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableObserver<Object>() {
//                    @Override
//                    public void onComplete() {
//
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                    @Override
//                    public void onNext(Object movieSubject) {
//                        Log.d("lsc", "onResponse" + movieSubject.toString());
//                    }
//                });
//        stringCall.enqueue(new Callback<Object>() {
//
//            @Override
//            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
//                Log.d("lsc", "onResponse" + response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Log.d("lsc", "onResponse" + t.toString());
//            }
//        });
    }

}
