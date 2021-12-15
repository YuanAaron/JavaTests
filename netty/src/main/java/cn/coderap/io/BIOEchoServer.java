package cn.coderap.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOEchoServer {
    public static void main(String[] args) throws IOException {
        // 启动服务端，绑定8000端口
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("server start...");

        while (true) {
            // 开始接受客户端连接
            Socket socket = serverSocket.accept();
            System.out.println("one client conn:" + socket);

            // 启动线程处理连接数据
            new Thread(() -> {
                //读取数据
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String msg;
                    while ((msg = bufferedReader.readLine()) != null) {
                        System.out.println("receive msg:" + msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
