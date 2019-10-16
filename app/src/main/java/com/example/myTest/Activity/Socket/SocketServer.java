package com.example.myTest.Activity.Socket;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer extends Thread {
    public ServerSocket serverSocket;
    public Socket socket;
    private int PORT ;

    public Context context;
    public Handler handler;

    public SocketServer(Context context, Handler handler, int PORT) {
        this.context = context;
        this.handler = handler;
        this.PORT = PORT;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT, 3);
            Log.e("wifi服务器", "已开启");

            //接收到消息后，马上发送一条消息
            while (true) {
                sleep(100);
                Log.e("wifi服务器=========》", "");
                socket = serverSocket.accept();
                Log.e("wifi服务器", "设备已连接");

                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                if ((len = in.read(buffer)) != -1) {
                    byte[] data = new byte[len];
                    for (int i = 0; i < data.length; i++)
                        data[i] = buffer[i];
                    String msg = new String(data);
                    Log.e("收到消息", msg);
                    //通过handle来弹出Toast。
                    Message message = new Message();
                    message.obj = msg;
                    message.what = 1;
                    handler.sendMessage(message);

                    //发送消息
                    out.write("对方已接收消息".getBytes());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}