package com.example.myTest.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.example.myTest.R;

public class WebViewActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.test_web_View)
    private WebView webView;

    @ViewInject(R.id.jsLocate_button_web_View)
    private Button jsLocate_button_web_View;

    @ViewInject(R.id.jsSearch_button_web_View)
    private Button jsSearch_button_web_View;

    @ViewInject(R.id.simpleMap_button_web_View)
    private Button simpleMap_button_web_View;

    private WebSettings webSettings;

    private String lat;
    private String lng;
    private double latitude = 0.0;
    private double longitude = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ViewUtils.inject(this);


        jsLocate_button_web_View.setOnClickListener(this);
        jsSearch_button_web_View.setOnClickListener(this);
        simpleMap_button_web_View.setOnClickListener(this);


        webOperate();
    }

    @SuppressLint("JavascriptInterface")
    private void webOperate() {
//        file:///android_asset/web/    文件名、目录   =====》 小写  《=====
//        http://120.26.60.230:8180/dist/index.html#/
//        http://120.26.60.230:8180/dist/appindex.html#/
        Log.d("activity_main_test", "webOperate: ");
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://120.26.60.230:8180/dist/appindex.html#/");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {


//        "coordinateX": "122.140462",
//         "coordinateY": "37.5540950",
        switch (v.getId()) {
            case R.id.jsLocate_button_web_View:
                Log.d("activity_main_test", "onClick: 1111111111111");
                getlocate();
                webView.loadUrl("javascript:jsLocate('" + lat + "','" + lng + "')");
                break;

            case R.id.jsSearch_button_web_View:
                Log.d("activity_main_test", "onClick: 222222222222");
                webView.loadUrl("javascript:jsSearch('" + "富海小区" + "')");
                break;
            case R.id.simpleMap_button_web_View:
                Log.d("activity_main_test", "onClick: 333333333333");
//                simpleMap("陈家山村E", "121.960616", "37.3322972")
//                webView.loadUrl("javascript:simpleMap('" + travelGsonData.getName() + "','" + travelGsonData.getLat() + "','" +
//                        travelGsonData.getLng() + "')");
                webView.loadUrl("javascript:simpleMap('" + "陈家山村E" + "','" + "37.3322972" + "','" + "121.960616" + "')");
//                webView.loadUrl("javascript:jsSearch('" + "富海小区" + "')");
                break;
        }
    }

    /**
     * 获取经纬度
     */
    private void getlocate() {
        LocationManager locationManager = (LocationManager) WebViewActivity.this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                lat = String.valueOf(latitude);
                lng = String.valueOf(longitude);
            }
        } else {
            LocationListener locationListener = new LocationListener() {

                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {

                }


                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {

                }

                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    lat = String.valueOf(location.getLatitude());
                    lng = String.valueOf(location.getLongitude());
                }
            };
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude(); //经度
                longitude = location.getLongitude(); //纬度
                lat = String.valueOf(latitude);
                lng = String.valueOf(longitude);
            }

        }
    }
}
