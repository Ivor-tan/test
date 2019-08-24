package com.example.myTest.constant;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface API_Constants {
    @GET(Constants.URL.Dofuntech_URL + Constants.API.GET_TOKEN)
    Observable<Object> getToken();

}
