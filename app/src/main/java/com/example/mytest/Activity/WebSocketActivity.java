package com.example.myTest.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.test.my.R;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebSocketActivity extends Activity {

    private WebSocketManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websocket_test);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        WebSocketSetting setting = new WebSocketSetting();
        //连接地址，必填，例如 wss://localhost:8080
        setting.setConnectUrl("ws://123.207.167.163:9010/ajaxchattest");

        manager = WebSocketHandler.init(setting);


    }

    @OnClick({R.id.web_socket_connect})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.web_socket_connect:
                //启动连接
                manager.start();
                break;
        }
    }

}
