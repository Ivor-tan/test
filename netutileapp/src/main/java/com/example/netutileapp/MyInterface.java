package com.example.netutileapp;

import com.example.netutileapp.bean.ChoseTypeBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyInterface {

    @GET("/charm-mmc/api/v1/app/whAppUser/getEncryptToken?tokenKey=get")
    Observable<Object> login();

    @GET("/community/api/v1/dict?dictType=mutual_help")
    Observable<ChoseTypeBean> getHelpType();

    @GET("/community/api/v1/dict?dictType=idle_trading")
    Observable<ChoseTypeBean> getXzType();

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
