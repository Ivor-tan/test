package com.example.myTest.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.myTest.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static android.support.constraint.Constraints.TAG;

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
        x.view().inject(this);


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
        webView.loadUrl("file:///android_asset/map1/index.html");
//        webView.loadUrl("file:///android_asset/ParticleEffect/index.html");

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


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.d("tbq", "onLocationChanged: " + aMapLocation.getLatitude() + "======" + aMapLocation.getLongitude());
            Log.d("AmapError", "location Error, ErrCode: + amapLocation.getErrorCode() + " + aMapLocation.getErrorInfo());
        }
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    /**
     * 获取经纬度
     */
    private void getlocate() {

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        AMapLocationClientOption option = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
//        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

//
//        //获取系统的服务，
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        //创建一个criteria对象
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//        //设置不需要获取海拔方向数据
//        criteria.setAltitudeRequired(false);
//        criteria.setBearingRequired(false);
//        //设置允许产生资费
//        criteria.setCostAllowed(true);
//        //要求低耗电
//        criteria.setPowerRequirement(Criteria.POWER_LOW);
//        String provider = locationManager.getBestProvider(criteria, true);
//        Log.i("tbq", "Location Provider is " + provider);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location location = locationManager.getLastKnownLocation(provider);
//        Log.d("tbq", "getlocate: " + location);
//
//        if (location != null) {
//            Log.d("tbq", "getlocate: " + location.getLatitude() + "=======" + location.getLongitude());
//        }
//        LocationManager locationManager = (LocationManager) WebViewActivity.this.getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            Log.d("tbq", "getlocate: 111111111111");
//            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (location != null) {
//                latitude = location.getLatitude();
//                longitude = location.getLongitude();
//                lat = String.valueOf(latitude);
//                lng = String.valueOf(longitude);
//            }
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            Log.d("tbq", "getlocate: 333333");
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
//        } else {
//            Log.d("tbq", "getlocate: 2222222222");
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
//            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            if (location != null) {
//                latitude = location.getLatitude(); //经度
//                longitude = location.getLongitude(); //纬度
//                lat = String.valueOf(latitude);
//                lng = String.valueOf(longitude);
//            }
//
//        }
//        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled: " + provider + "tbq.." + Thread.currentThread().getName());
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled: " + provider + "tbq.." + Thread.currentThread().getName());
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged: " + "tbq.." + location.getLongitude() + "========" + location.getLatitude());
            //如果位置发生变化,重新显示
//            showLocation(location);
        }
    };

}
