package com.example.myTest.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bravin.btoast.BToast;
import com.example.myTest.Utils.DownLoadManager.DownLoadManager;
import com.example.myTest.Utils.Http.RetrofitUtil.RequestLoader;
import com.example.myTest.bean.RxJava_test_bean;
import com.example.myTest.constant.Constants;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.example.myTest.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class RxJavaActivity extends Activity {
    @ViewInject(R.id.RxJava_RecyclerView_OnNext_test)
    private RecyclerView RxJava_RecyclerView_OnNext_test;

    @ViewInject(R.id.RxJava_data1)
    private Button RxJava_data1;

    @ViewInject(R.id.RxJava_data2)
    private Button RxJava_data2;

    @ViewInject(R.id.RxJava_data3)
    private Button RxJava_data3;

    @ViewInject(R.id.RxJava_data4)
    private Button RxJava_data4;

    @ViewInject(R.id.RxJava_data5)
    private Button RxJava_data5;

    @ViewInject(R.id.RxJava_Progress)
    private TextView RxJava_Progress;

    private final String TAG = "RxJavaActivity---->";

    private CommonAdapter<RxJava_test_bean> commonAdapter;
    private List<RxJava_test_bean> data_list = new ArrayList<>();

    private String apk_download = "http://gdown.baidu.com/data/wisegame/";
    private String apk = "ab30ed59c5f341f4/baidushoujizhushou_16797445.apk";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_test);
        ViewUtils.inject(this);

        initView();
//        initData();
//        initData2();
//        initData3();
    }

    @OnClick({R.id.RxJava_data1, R.id.RxJava_data2, R.id.RxJava_data3, R.id.RxJava_data4, R.id.RxJava_data5})
    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.RxJava_data1:
                initData1();
                break;
            case R.id.RxJava_data2:
                initData2();
                break;
            case R.id.RxJava_data3:
                initData3();
                break;
            case R.id.RxJava_data4:
                initData4();
                break;
            case R.id.RxJava_data5:
                initData5();
                break;
        }
    }

    //文件下载
    private void initData5() {
        RequestLoader requestLoader = new RequestLoader("", apk_download);

        Observable<Boolean> observable = requestLoader.mMovieService.retrofitDownloadFile(apk)
                .map(new Function<ResponseBody, Boolean>() {
                    @Override
                    public Boolean apply(ResponseBody responseBody) throws Exception {

                        initData5_updateView(responseBody);

                        return true;
                    }
                });
//
        requestLoader.toSubscribe(observable, new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                Log.d(TAG, "onNext: " + aBoolean + "=======>" + (getFilesDir() + "/" + String.valueOf(System.currentTimeMillis()) + ".apk"));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: " + "=======>");
            }
        });

    }

    private void initData5_updateView(ResponseBody responseBody) {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                DownLoadManager.writeResponseBodyToDisk(System.currentTimeMillis() + ".apk", responseBody, new DownLoadManager.ProgressListener() {
                    @Override
                    public void onProgress(int progress) {
                        emitter.onNext(progress);
                    }
                });
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在io线程
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        RxJava_Progress.setText(integer + "%");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        BToast.success(RxJavaActivity.this)
                                .showIcon(false)
                                .text("onComplete")
                                .target(RxJava_data5)
                                .animate(true)
                                .show();
                    }
                });
    }

    private void initData4() {

        RequestLoader requestLoader = new RequestLoader("", Constants.URL.Dofuntech_URL);

        Observable<RxJava_test_bean> observable = requestLoader.mMovieService.login()
                .doOnNext(new Consumer<RxJava_test_bean>() {
                    @Override
                    public void accept(RxJava_test_bean rxJava_test_bean) throws Exception {
                        Log.d(TAG, "accept: ====>" + rxJava_test_bean.getCode());
                    }

                });

        requestLoader.toSubscribe(observable, new DisposableObserver<RxJava_test_bean>() {
            @Override
            public void onNext(RxJava_test_bean rxJava_test_bean) {
                Log.d(TAG, "onNext  -----" + data_list.size());
                data_list.add(rxJava_test_bean);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onComplete  -----" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete  -----");
                commonAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initData3() {
        ObservableOnSubscribe<String> test = new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1111111111");
                emitter.onNext("222222");
                emitter.onNext("33333333");
                emitter.onComplete();
            }
        };

        Observable.create(test)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {

                        Log.d(TAG, "onNext" + s + "-----" + data_list.size());
//                        RxJava_test_bean bean = new RxJava_test_bean();
//                        bean.s(s);
//                        data_list.add(bean);


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                        commonAdapter.notifyDataSetChanged();
                    }
                });

    }


    private void initData2() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在io线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        Log.e(TAG, "onNext:" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError=" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete()");
                    }
                });
    }


    Disposable mDisposable;

    private void initData1() {


        //被观察者
        Observable novel = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        });


        //观察者
        Observer<String> reader = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                if ("2".equals(value)) {
                    mDisposable.dispose();
                    return;
                }
                Log.e(TAG, "onNext:" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError=" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete()");
            }
        };

        novel.subscribe(reader);//一行代码搞定
    }


    private void initData() {
//      String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxNTA3MTAyNzYyMCIsImlhdCI6MTU2NTkyODc0Niwic3ViIjoie1widXNlcklkXCI6NDY0MH0iLCJpc3MiOiJSSF9CVVNTSU5FU1NfSldUIiwiZXhwIjoxNTY4NTIwNzQ2fQ.L4GsMN7dSFU7IkDqnakH61OiHGSI0p0n94S50RfCBC8";
//        RequestLoader requestLoader = new RequestLoader("");
//        Observable<Object> objectObservable = requestLoader.mMovieService.login();
//        requestLoader.toSubscribe(objectObservable, new DisposableObserver<Object>() {
//            @Override
//            public void onNext(Object o) {
//                Log.d("test", "onNext: 000" + o.toString());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("test", "onNext:11111 ");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d("test", "onNext:22222 ");
//            }
//        });
//
//        requestLoader.toSubscribe(objectObservable, new DisposableObserver<T>() {
//        });


    }

    private void initView() {

        commonAdapter = new CommonAdapter<RxJava_test_bean>(this, R.layout.item_rxjava_test, data_list) {
            @Override
            protected void convert(ViewHolder holder, RxJava_test_bean rxJava_test_bean, int position) {
                holder.setText(R.id.item_RxJava_onNext, rxJava_test_bean.getData().getTokenInfo().getTokenKey());
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RxJava_RecyclerView_OnNext_test.setLayoutManager(linearLayoutManager);
        RxJava_RecyclerView_OnNext_test.setAdapter(commonAdapter);

    }
}
