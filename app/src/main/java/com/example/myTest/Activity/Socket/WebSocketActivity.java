package com.example.myTest.Activity.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myTest.R;
import com.zhangke.websocket.SocketListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;
import com.zhangke.websocket.response.ErrorResponse;

import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebSocketActivity extends Activity {


    @BindView(R.id.web_text_show)
    TextView test_show;

    @BindView(R.id.send_msg)
    EditText send_msg;

    @BindView(R.id.web_send_text)
    TextView send;

    private WebSocketManager manager;
    private MySocketListener mySocketListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websocket_test);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        try {
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMzQzNzEzNjQ4MSIsImlhdCI6MTU2NTkyMzQ0MCwic3ViIjoie1widXNlcklkXCI6MjgxNX0iLCJpc3MiOiJKV1RfUkhfQlVTU0lORVNTX0pXVCIsImV4cCI6MTU2ODUxNTQ0MH0.QpM-WfI-ViTHZU7dnOGGFfoAnPAt3bnCbALFJkMv5rk";
            String test_URL = "ws://116.255.145.242:8801/ws/imCommunity?authorization=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMzQzNzEzNjQ4MSIsImlhdCI6MTU2NTkyMzQ0MCwic3ViIjoie1widXNlcklkXCI6MjgxNX0iLCJpc3MiOiJKV1RfUkhfQlVTU0lORVNTX0pXVCIsImV4cCI6MTU2ODUxNTQ0MH0.QpM-WfI-ViTHZU7dnOGGFfoAnPAt3bnCbALFJkMv5rk";

            Map<String, String> heard = new HashMap<>();
            heard.put("authorization", token);
//            Log.d("test", "init: " + heard.toString());
            WebSocketSetting setting = new WebSocketSetting();

            //连接地址，必填，例如 wss://localhost:8080
            //wss://echo.websocket.org  测试地址
//            ws://127.0.0.1:8088/ws/imCommunity
//            String ss = URLEncoder.encode("ws://123.207.167.163:9010/ajaxchattest", "utf-8");
            setting.setConnectUrl(test_URL);

//            setting.setHttpHeaders(heard);

            manager = WebSocketHandler.init(setting);

            mySocketListener = new MySocketListener();
            manager.addListener(mySocketListener);
        } catch (Exception e) {
        }
    }

    @OnClick({R.id.web_socket_connect, R.id.web_send_text})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.web_socket_connect:
                //启动连接
                manager.start();
                break;

            case R.id.web_send_text:
                manager.send(send_msg.getText().toString().trim());
                break;
        }
    }

    private class MySocketListener implements SocketListener {


        @Override
        public void onConnected() {
            Toast.makeText(WebSocketActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
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

            test_show.setText(message + "<========>" + formatStr);
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
