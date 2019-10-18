package com.example.myTest.Activity.Socket;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.bravin.btoast.BToast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class SocketClient extends Thread {
    private int port;
    private String host;
    private InputStream in;
    private OutputStream out;
    private Handler handler;
    public final static int CLIENT_MSG = 1;
    private Context context;
    private Socket socket;



    /*
    * Socket 一个端口号只能绑定一个    可优化复用
    * 输出  输入流的关闭
    * */

    public SocketClient(Context context, int port, String host, Handler handler) {
        this.host = host;
        this.port = port;
        this.context = context;
        this.handler = handler;
        initData();
    }

    public SocketClient() {
        super();
    }


    private void initData() {

    }

    public void Send(String msg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("tbq", "run: 111" + msg + socket.isConnected() + socket.toString());

                    out = socket.getOutputStream();
                    out.write(msg.getBytes());
                    out.flush();
//                        Log.d("tbq", "run: 222");
//                        out.flush();
//                        Log.d("tbq", "run: 333");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void run() {
//        super.run();
//        while (true) {
        try {
            Thread.sleep(1000);
            socket = new Socket(host, port);
            BToast.success(context)
                    .text("客户端===>建立连接")
                    .show();
            Log.e("wifisocket", "tbq   建立连接" + socket.toString());

            in = socket.getInputStream();
//            OutputStream out = socket.getOutputStream();

//            out.write("6666666".getBytes());
//            for (int i = 0; i < 10; i++) {
//
//            }
            //接收消息
            while (true) {
                Log.d("tbq", "run:3333333333 " + socket.getLocalPort());
                byte[] buffer = new byte[1024];
                int len = 0;
                if ((len = in.read(buffer)) != -1) {
                    byte[] data = new byte[len];
                    for (int i = 0; i < data.length; i++) {
                        data[i] = buffer[i];
                    }
                    String msg = new String(data);
                    Message message = new Message();
                    message.what = CLIENT_MSG;
                    message.obj = msg;
                    handler.sendMessage(message);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        }
//            try {
//                //创建一个流套接字并将其连接到指定主机上的指定端口号
//                socket = new Socket(host, port);
//
//                //读取服务器端数据
//                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                //向服务器端发送数据
//                PrintStream out = new PrintStream(socket.getOutputStream());
////                System.out.print("请输入: \t");
//                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
//                out.println("5555555");
//
//                String ret = input.readLine();
//                System.out.println("服务器端返回过来的是: " + ret);
//                // 如接收到 "OK" 则断开连接
//                if ("OK".equals(ret)) {
//                    System.out.println("客户端将关闭连接");
//                    Thread.sleep(500);
//                    break;
//                }
//
//                out.close();
//                input.close();
//            } catch (Exception e) {
//                System.out.println("客户端异常:" + e.getMessage());
//            } finally {
//                if (socket != null) {
//                    try {
//                        socket.close();
//                    } catch (IOException e) {
//                        socket = null;
//                        System.out.println("客户端 finally 异常:" + e.getMessage());
//                    }
//                }
//            }
    }
}

//    public static void main(String[] args) {
//        System.out.println("SocketClient Start...");
//        while (true) {
//            Socket socket = null;
//            try {
//                //创建一个流套接字并将其连接到指定主机上的指定端口号
//                socket = new Socket(host, port);
//
//                //读取服务器端数据
//                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                //向服务器端发送数据
//                PrintStream out = new PrintStream(socket.getOutputStream());
//                System.out.print("请输入: \t");
//                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
//                out.println(str);
//
//                String ret = input.readLine();
//                System.out.println("服务器端返回过来的是: " + ret);
//                // 如接收到 "OK" 则断开连接
//                if ("OK".equals(ret)) {
//                    System.out.println("客户端将关闭连接");
//                    Thread.sleep(500);
//                    break;
//                }
//
//                out.close();
//                input.close();
//            } catch (Exception e) {
//                System.out.println("客户端异常:" + e.getMessage());
//            } finally {
//                if (socket != null) {
//                    try {
//                        socket.close();
//                    } catch (IOException e) {
//                        socket = null;
//                        System.out.println("客户端 finally 异常:" + e.getMessage());
//                    }
//                }
//            }
//        }
//    }
//}