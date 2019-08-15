package com.example.myTest.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.test.my.R;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;

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

            case R.id.web_send_text:
                manager.send(send_msg.getText().toString().trim());
                break;
        }
    }

}
