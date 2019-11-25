package com.example.myTest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bravin.btoast.BToast;
import com.example.myTest.Activity.AudioActivity;
import com.example.myTest.Activity.CameraActivity;
import com.example.myTest.Activity.ImageActivity;
import com.example.myTest.Activity.QR_codeActivity;
import com.example.myTest.Activity.RxJavaActivity;
import com.example.myTest.Activity.SharedPreferencesActivity;
import com.example.myTest.Activity.Socket.SocketTestActivity;
import com.example.myTest.Activity.SystemContactActivity;
import com.example.myTest.Activity.Socket.WIFITestActivity;
import com.example.myTest.Activity.Socket.WebSocketActivity;
import com.example.myTest.Activity.WebViewActivity;
import com.example.myTest.Listener.PermissionListener;
import com.example.myTest.Utils.Http.RetrofitUtil.RequestLoader;
import com.example.myTest.Utils.map.LocationUtils;


import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;

public class MainTest extends Activity implements View.OnClickListener {

    private String TAG = "MainTest";

    private static PermissionListener mListener;
    private static Activity activity;


    @ViewInject(R.id.textView)
    private TextView textView;

    @ViewInject(R.id.main_EditText)
    private EditText main_EditText;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        activity = this;

        initView();
        initData();
        hideInput();


        Log.d(TAG, "onCreate: tbq==============="+LocationUtils.getInstance().getLocations(this));
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "ACTION_DOWN: ");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "ACTION_UP: ");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "ACTION_MOVE: ");
                        break;
                    case MotionEvent.ACTION_OUTSIDE:
                        Log.d(TAG, "ACTION_OUTSIDE: ");
                        break;

                }
                return MainTest.super.onTouchEvent(event);
            }
        });

    }

    private void initView() {
        x.view().inject(this);
        int[] ids = new int[]{
                R.id.WIFI,
                R.id.SharedPreferences,
                R.id.Random,
                R.id.QR_code,
                R.id.RxJava,
                R.id.image_view,
                R.id.time_picker,
                R.id.system_contact,
                R.id.web_View,
                R.id.camera,
                R.id.Permissions,
                R.id.audio_test,
                R.id.socket,
                R.id.web_socket
        };
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
//        activity_main_test = findViewById(R.id.activity_main_test);
//        activity_main_test.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        activity_main_test.setText("11111");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        activity_main_test.setText("22222");
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.WIFI:
                startActivity(new Intent(this, WIFITestActivity.class));
                break;
            case R.id.SharedPreferences:
                startActivity(new Intent(this, SharedPreferencesActivity.class));
                break;
            case R.id.Random:
                if (main_EditText.getText().toString().equals("")) {
                    BToast.success(this)
                            .text("请输入范围")
                            .show();
                    return;
                }
                Random random = new Random();
                textView.setText((random.nextInt(Integer.valueOf(main_EditText.getText().toString())) + 1) + "");

                break;
            case R.id.QR_code:
                startActivity(new Intent(this, QR_codeActivity.class));
                break;

            case R.id.RxJava:
                startActivity(new Intent(this, RxJavaActivity.class));
                break;
            case R.id.web_socket:
                startActivity(new Intent(this, WebSocketActivity.class));
                break;
            case R.id.socket:
                startActivity(new Intent(this, SocketTestActivity.class));
                break;
            case R.id.camera:
                startActivity(new Intent(this, CameraActivity.class));
                break;
            case R.id.web_View:
                Intent webView = new Intent(this, WebViewActivity.class);
                startActivity(webView);
                break;
            case R.id.image_view:
                Intent intent = new Intent(this, ImageActivity.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.Permissions:
                if (Build.VERSION.SDK_INT >= 23) {//判断当前系统是不是Android6.0
                    requestRuntimePermissions(PERMISSIONS_STORAGE, new PermissionListener() {
                        @Override
                        public void granted() {
                            //权限申请通过
                        }

                        @Override
                        public void denied(List<String> deniedList) {
                            //权限申请未通过
//                            for (String denied : deniedList) {
//                                if (denied.equals("android.permission.ACCESS_FINE_LOCATION")) {
//                                    Toast.makeText(activity_main_test.this, "start", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(activity_main_test.this, "start", Toast.LENGTH_SHORT).show();
//                                }
//                            }
                            Log.d("activity_main_test", "denied: " + deniedList.toString());
                        }
                    });
                }
                break;

            case R.id.audio_test:
                startActivity(new Intent(this, AudioActivity.class));
                break;
            case R.id.system_contact:
                startActivity(new Intent(this, SystemContactActivity.class));
                break;

            case R.id.time_picker:

                ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
                ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
                ScheduledExecutorService newScheduledThreadPool =
                        Executors.newScheduledThreadPool(4);
                ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();

                Semaphore semaphore = new Semaphore(5, true);
//                semaphore.

                final List<String> list = new ArrayList<String>();
                List<String> proxyInstance = (List<String>)
                        Proxy.newProxyInstance(list.getClass().getClassLoader(),
                                list.getClass().getInterfaces(), new InvocationHandler() {
                                    @Override
                                    public Object invoke(Object proxy, Method method, Object[] args) throws
                                            Throwable {
                                        return method.invoke(list, args);
                                    }
                                });
                proxyInstance.add("你好");
                System.out.println(list);


                //ok http  实例
//                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
//                String requestBody = "{"type":"1"}";
//                JSONObject requestBody = new JSONObject();
//                JsonHelper.put(requestBody, "type", "3");
//                JsonHelper.put(requestBody, "mobile", "15527017729");
//                String aaa = "15527017729";
//                String bbb = "2";

//                Log.d("activity_main_test", "onClick: " + "http://120.26.60.230:8180/charm-mmc/api/v1/common/sms/vcode/" + aaa + "," + bbb);

//                Request request = new Request.Builder()
//                        .url("http://120.26.60.230:8180/charm-mmc/api/v1/app/whAppUser/getEncryptToken?tokenKey=get")
//                        .get()
//                        .build();
//                OkHttpClient okHttpClient = new OkHttpClient();
//                okHttpClient.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.d("activity_main_test", "onFailure: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Log.d("activity_main_test", response.protocol() + " " + response.code() + " " + response.message());
//                        Headers headers = response.headers();
//                        for (int i = 0; i < headers.size(); i++) {
//                            Log.d("activity_main_test", headers.name(i) + ":" + headers.value(i));
//                        }
////                        Log.d("activity_main_test", "onResponse: " + response.body().string());
//
//                        String sss = response.body().string();
//                        Log.d("activity_main_test", "onResponse: " + sss);
//                        TokenBean bean = GsonUtils.jsonToBean(sss, TokenBean.class);
//
//                        Log.d("activity_main_test", "onResponse: " + bean.getData().getTokenInfo().getUserKey());
//
//                    }
//                });
                showPickTime();
                break;
        }
    }

    protected void hideInput() {

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 申请后的处理
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            List<String> deniedList = new ArrayList<>();
            // 遍历所有申请的权限，把被拒绝的权限放入集合
            for (int i = 0; i < grantResults.length; i++) {
                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    mListener.granted();
                } else {
                    deniedList.add(permissions[i]);
                }
            }
            if (!deniedList.isEmpty()) {
                mListener.denied(deniedList);
            }
        }
    }


    /**
     * 申请权限
     */
    public static void requestRuntimePermissions(
            String[] permissions, PermissionListener listener) {
        mListener = listener;
        List<String> permissionList = new ArrayList<>();
        // 遍历每一个申请的权限，把没有通过的权限放在集合中
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            } else {
                mListener.granted();
            }
        }
        // 申请权限
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    permissionList.toArray(new String[permissionList.size()]), 1);
        }
    }

    /**
     * 时间选择器
     */
    private void showPickTime() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("1111111");
        }
        for (int i = 0; i < 5; i++) {
            list.add("22222222");
        }
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list2.add("444444");
        }
        for (int i = 0; i < 5; i++) {
            list2.add("555555555");
        }
        List<List<String>> list1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list1.add(list2);
        }
        for (int i = 0; i < 5; i++) {
            list1.add(list);
        }

        OptionsPickerView pickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                Log.d("tbq", "onOptionsSelect: " + list.get(options1));
            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {
                Log.d("tbq", "onOptionsSelect: " + list.get(options1) + list1.get(options1).get(options2));
            }
        }).build();

        pickerView.setPicker(list, list1);

        pickerView.show();


//        TimePickerView timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {
//                SimpleDateFormat formatter = new SimpleDateFormat("YYYY年MM月dd日 HH点mm分");
//                textView.setText(formatter.format(date));
////                Log.d("activity_main_test", "onTimeSelect: " + formatter.format(date));
//            }
//        }).setTitleText("上门时间")
//                .setType(new boolean[]{true, true, true, true, true, false})
//                .setBgColor(Color.rgb(255, 255, 255))
//                .setTitleBgColor(Color.rgb(255, 255, 255)).build();
//        timePickerView.show();
    }


    /**
     * 6.0以后系统权限申请 清单
     */
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.CHANGE_NETWORK_STATE",

            "android.permission.WRITE_SETTINGS",
            "android.permission.READ_CONTACTS",
            "android.permission.WRITE_CONTACTS",
            "android.permission.GET_ACCOUNTS",
            "android.permission.CAMERA",
            "android.permission.CHANGE_WIFI_STATE",
            "android.permission.RECORD_AUDIO",
            "android.permission.INTERNET",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.CAMERA"};

//     <!--wifi相关-->
//    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
//    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
//    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
//    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
//    <!--//允许修改系统设置-->
//    <uses-permission android:name="android.permission.WRITE_SETTINGS"
//    tools:ignore="ProtectedPermissions" />

}
