package com.example.myTest.Utils.Http.RetrofitUtil;

import com.example.myTest.bean.RxJava_test_bean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface MyInterface {

    @GET("api/v1/app/whAppUser/getEncryptToken?tokenKey=get")
    Observable<RxJava_test_bean> login();

    @Streaming
    @GET
    Observable<ResponseBody> retrofitDownloadFile(@Url String fileUrl);

//    @GET("/community/api/v1/dict?dictType=mutual_help")
//    Observable<ChoseTypeBean> getHelpType();
//
//    @GET("/community/api/v1/dict?dictType=idle_trading")
//    Observable<ChoseTypeBean> getXzType();

    @FormUrlEncoded
    @POST("/community/api/v1/apiaddIdleTrading")
    Observable<Object> addXz(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/community/api/v1/addHelp")
    Observable<Object> addHelp(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/community/api/v1/activePos")
    Observable<Object> get(@FieldMap Map<String, String> map);
}
