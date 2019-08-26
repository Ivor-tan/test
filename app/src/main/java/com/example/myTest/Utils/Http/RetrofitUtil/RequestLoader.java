package com.example.myTest.Utils.Http.RetrofitUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RequestLoader  {

    public MyInterface mMovieService;

    public RequestLoader(String tokens,String url) {
        mMovieService = new RetrofitServiceManager(tokens,url).create(MyInterface.class);
    }
    public <T> void toSubscribe(Observable<T> o, DisposableObserver<T> s) {

        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)//请求失败重连次数
                .subscribe(s);

    }


}

