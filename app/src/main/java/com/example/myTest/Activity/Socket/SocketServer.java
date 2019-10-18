package com.example.myTest.Activity.Socket;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.bravin.btoast.BToast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class SocketServer extends Thread {
    public ServerSocket serverSocket;
    public Socket socket;
    private int PORT;
    private int LockPort;

    private InputStream in;
    private OutputStream out;
    public Context context;
    public Handler handler;
    public final static int SERVER_MSG = 2;

    /*
     * Socket 一个端口号只能绑定一个    可优化复用
     * 输出  输入流的关闭
     * */

    public SocketServer(Context context, Handler handler, int PORT) {
        this.context = context;
        this.handler = handler;
        this.PORT = PORT;
    }

    public void Send(String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out = socket.getOutputStream();
                    out.write(msg.getBytes());
                    out.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
//            Log.e("wifi服务器", "已开启");
            BToast.success(context)
                    .text("服务器===>已开启")
                    .show();
//            Log.d("tbq", "run:000000000 " + socket);
            while (true) {
//                Thread.sleep(1000);
                if (socket == null || LockPort != socket.getLocalPort()) {
                    Log.d("tbq", "run:1111111111 ");
                    socket = serverSocket.accept();
                    LockPort = socket.getLocalPort();
                    BToast.success(context)
                            .text("wifi服务器===>设备已连接")
                            .show();
                } else if (LockPort != socket.getLocalPort()) {
                    Log.d("tbq", "run:2222222222 ");
                    socket = serverSocket.accept();
                    LockPort = socket.getLocalPort();
                    BToast.success(context)
                            .text("wifi服务器===>设备已连接")
                            .show();
                }
                Log.d("tbq", "run:3333333333 " + socket.getLocalPort());
                in = socket.getInputStream();
//                out = socket.getOutputStream();
//                out.write("收到消息".getBytes(StandardCharsets.UTF_8));
                byte[] buffer = new byte[1024];
                int len = 0;
                if ((len = in.read(buffer)) != -1) {
                    byte[] data = new byte[len];
                    for (int i = 0; i < data.length; i++)
                        data[i] = buffer[i];
//                    String msg = new String(data);
//                    Log.e("收到消息", msg);
                    //通过handle来弹出Toast。
                    Message message = new Message();
                    message.obj = new String(data);
                    message.what = SERVER_MSG;
                    handler.sendMessage(message);
                    //发送消息
//                    out.write("对方已接收消息".getBytes());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}