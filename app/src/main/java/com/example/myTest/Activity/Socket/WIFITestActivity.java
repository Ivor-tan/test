package com.example.myTest.Activity.Socket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myTest.R;
import com.example.myTest.Utils.WiFi.WifiUtil;
import com.zhangke.websocket.SocketListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;
import com.zhangke.websocket.response.ErrorResponse;

import org.java_websocket.framing.Framedata;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WIFITestActivity extends Activity {
    private String TAG = "WIFITestActivity";
    private WifiUtil mWifiUtil;

    @ViewInject(R.id.createWifiHot)
    private Button createWifiHot;

    @ViewInject(R.id.close_WifiHot)
    private Button close_WifiHot;
    @ViewInject(R.id.connect_WifiHot)
    private Button connect_WifiHot;

    @ViewInject(R.id.WifiHot_Socket_Server)
    private Button Socket_Server;

    @ViewInject(R.id.WifiHot_Socket_Client_Send)
    private Button Socket_Client_Send;


    private String password = "123456789";
    private String name = "testHot";

    private Handler handler;

    private WebSocketManager SocketManager;
    private WIFITestActivity.MySocketListener mySocketListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_test);
        x.view().inject(this);
        initData();
    }

    private void initData() {
        mWifiUtil = new WifiUtil(this);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG, "test   handleMessage: " + msg.obj.toString());
            }
        };

    }


    @Event({R.id.createWifiHot, R.id.close_WifiHot, R.id.connect_WifiHot, R.id.WifiHot_Socket_Server, R.id.WifiHot_Socket_Client_Send})
    private void OnClick(View view) {
        switch (view.getId()) {

            case R.id.WifiHot_Socket_Client_Send:
                WebSocketSetting setting = new WebSocketSetting();
                setting.setConnectUrl(mWifiUtil.getIPAddress()+":1234");
                SocketManager = WebSocketHandler.init(setting) ;

                SocketManager.send("55555");

                break;

            case R.id.WifiHot_Socket_Server:
                Log.d(TAG, "test    OnClick: " + mWifiUtil.getIPAddress());
                SocketServer socketServer = new SocketServer(this, handler);
                socketServer.start();
//                setting.setConnectUrl(test_URL);;
                break;
            case R.id.createWifiHot:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.System.canWrite(this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                Uri.parse("package:" + getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(intent, 1);
                    } else {
                        Log.d("test", "open: ");
                        //有了权限，你要做什么呢？具体的动作
                        mWifiUtil.createWifiHot(name, password, WifiUtil.WPA);
                    }
                }

                break;
            case R.id.close_WifiHot:
                Log.d("test", "close: ");

                break;
            case R.id.connect_WifiHot:
                Log.d("test", "connect: ");
                mWifiUtil.OpenWifi();

                boolean success = mWifiUtil.connectNet(name, password, WifiUtil.WPA);
                Log.d("test", "connect: " + success);
//                connectWiFi();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("test", "onActivityResult: " + requestCode + "===" + resultCode + "===");
    }

    private class MySocketListener implements SocketListener {


        @Override
        public void onConnected() {
            Toast.makeText(WIFITestActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
            Log.d("test2222222", "onMessage: ");
        }

        @Override
        public void onConnectFailed(Throwable e) {
            Log.d("test333333", "onMessage: ");
        }

        @Override
        public void onDisconnect() {
            Log.d("test4444444", "onMessage: ");
        }

        @Override
        public void onSendDataError(ErrorResponse errorResponse) {
            Log.d("test555555", "onMessage: ");
        }

        @Override
        public <T> void onMessage(String message, T data) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
            String formatStr = formatter.format(new Date());

//            test_show.setText(message + "<========>" + formatStr);
            Log.d("test", "onMessage: " + message);
        }

        @Override
        public <T> void onMessage(ByteBuffer bytes, T data) {
            Log.d("test1111", "onMessage: ");
        }

        @Override
        public void onPing(Framedata framedata) {
            Log.d("test16666666", "onMessage: ");
        }

        @Override
        public void onPong(Framedata framedata) {
            Log.d("test777777777", "onMessage: ");
        }
    }
}