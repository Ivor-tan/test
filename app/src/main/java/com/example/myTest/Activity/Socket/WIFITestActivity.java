package com.example.myTest.Activity.Socket;

import android.annotation.SuppressLint;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.myTest.R;
import com.example.myTest.Utils.Time.TimeUtil;
import com.example.myTest.Utils.WiFi.WifiUtil;
import com.example.myTest.bean.WiFiSocketMsgBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.myTest.Activity.Socket.SocketClient.CLIENT_MSG;
import static com.example.myTest.Activity.Socket.SocketServer.SERVER_MSG;

public class WIFITestActivity extends Activity {
    private String TAG = "WIFITestActivity";
    private WifiUtil mWifiUtil;
    private WifiManager mWifiManager;
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


    @ViewInject(R.id.connect_Socket_Server)
    private Button connect_Socket_Server;

    @ViewInject(R.id.WifiHot_Socket_Server_Send)
    private Button WifiHot_Socket_Server_Send;

    @ViewInject(R.id.WIFI_Socket_Msg)
    private RecyclerView WIFI_Socket_Msg;

    @ViewInject(R.id.WIFI_Socket_Msg_Text)
    private EditText WIFI_Socket_Msg_Text;

    private String password = "123456789";
    private String name = "testHot";
    private int PORT = 1234;

    private Handler handler;

    private SocketClient socketClient;
    private SocketServer socketServer;

    private CommonAdapter<WiFiSocketMsgBean> MsgAdapter;
    private List<WiFiSocketMsgBean> MsgData = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_test);
        x.view().inject(this);
        initData();

    }

    @SuppressLint("HandlerLeak")
    private void initData() {
        mWifiUtil = new WifiUtil(this.getApplicationContext());

        WiFiSocketMsgBean data = new WiFiSocketMsgBean();
        data.setDate(TimeUtil.dateFormat(new Date(System.currentTimeMillis())));
        data.setMsg("=========Start======");
        MsgData.add(data);
        MsgData.add(data);
        MsgData.add(data);

        MsgAdapter = new CommonAdapter<WiFiSocketMsgBean>(this, R.layout.item_wifi_socket_msg, MsgData) {
            @Override
            protected void convert(ViewHolder holder, WiFiSocketMsgBean data, int position) {
                holder.setText(R.id.item_wifi_socket_date, data.getDate() + "=====>")
                        .setText(R.id.item_wifi_socket_msg, data.getMsg());
            }
        };
        WIFI_Socket_Msg.setLayoutManager(new LinearLayoutManager(this));
        WIFI_Socket_Msg.setAdapter(MsgAdapter);


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                Log.e(TAG, "handleMessage: " + msg.what + msg.obj);
                switch (msg.what) {
                    case CLIENT_MSG:
                        WiFiSocketMsgBean client = new WiFiSocketMsgBean();
                        client.setDate(TimeUtil.dateFormat(new Date(System.currentTimeMillis())));
                        client.setMsg("SERVER_MSG:" + msg.obj);
                        MsgData.add(client);
                        MsgAdapter.notifyDataSetChanged();
                        break;
                    case SERVER_MSG:
                        WiFiSocketMsgBean server = new WiFiSocketMsgBean();
                        server.setDate(TimeUtil.dateFormat(new Date(System.currentTimeMillis())));
                        server.setMsg("CLIENT_MSG:" + msg.obj);
                        MsgData.add(server);
                        MsgAdapter.notifyDataSetChanged();
                        break;
                }

            }
        };

    }


    @Event({R.id.createWifiHot, R.id.close_WifiHot, R.id.connect_WifiHot, R.id.WifiHot_Socket_Server, R.id.WifiHot_Socket_Client_Send, R.id.connect_Socket_Server, R.id.WifiHot_Socket_Server_Send})
    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.WifiHot_Socket_Server_Send:
                String Server_text = WIFI_Socket_Msg_Text.getText().toString();
                if (!Server_text.equals("") && socketServer != null) {
                    WiFiSocketMsgBean server = new WiFiSocketMsgBean();
                    server.setDate(TimeUtil.dateFormat(new Date(System.currentTimeMillis())));
                    server.setMsg("SERVER_MSG:" + Server_text);
                    MsgData.add(server);
                    MsgAdapter.notifyDataSetChanged();
                    socketServer.Send(Server_text);
                }
                break;
            case R.id.WifiHot_Socket_Client_Send:
                String Client_text = WIFI_Socket_Msg_Text.getText().toString();
                if (!Client_text.equals("") && socketClient != null) {
                    WiFiSocketMsgBean client = new WiFiSocketMsgBean();
                    client.setDate(TimeUtil.dateFormat(new Date(System.currentTimeMillis())));
                    client.setMsg("CLIENT_MSG:" + Client_text);
                    MsgData.add(client);
                    MsgAdapter.notifyDataSetChanged();
                    socketClient.Send(Client_text);
                }

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            socketClient.getOut().write("6666".getBytes());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }) {
//                }.start();

                break;


            case R.id.connect_Socket_Server:
                socketClient = new SocketClient(getApplicationContext(), PORT, mWifiUtil.getServerAddress(), handler);
                socketClient.start();
                break;
            case R.id.WifiHot_Socket_Server:
                socketServer = new SocketServer(this.getApplicationContext(), handler, PORT);
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
//                        mWifiUtil.createWifiHot(name, password, WifiUtil.WPA);
                        mWifiUtil.createWifiHot(name, password);
                    }
                }

                break;
            case R.id.close_WifiHot:
                Log.d("test", "close: ");
                mWifiUtil.closeAp();
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

//    public void thread() {
//        final String ip = mWifiUtil.getIPAddress();
//        Log.d(TAG, "test   thread: " + mWifiUtil.getIPAddress());
//        final int port = 1234;
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Socket socket = new Socket(ip, port);
//                    Log.e("wifisocket", "建立连接");
//                    InputStream in = socket.getInputStream();
//                    out = socket.getOutputStream();
//                    out.write("55555".getBytes());
//                    //接收消息
//                    while (true) {
//                        byte[] buffer = new byte[1024];
//                        int len = 0;
//                        if ((len = in.read(buffer)) != -1) {
//                            byte[] data = new byte[len];
//                            for (int i = 0; i < data.length; i++)
//                                data[i] = buffer[i];
//                            String msg = new String(data);
//                            Log.e("收到消息", msg);
//
//                            Message message = new Message();
//                            message.what = 1;
//                            message.obj = msg;
//                            handler.sendMessage(message);
//                        }
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }.start();
//    }


}