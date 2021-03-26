package cn.meetdevelop.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * description: HelloServer
 * date: 2020/11/24 10:31
 * author: zgy
 * version: 1.0
 */
public class HelloServer {

    public void start(int port) {

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println(String.format("服务器在端口%d启动", port));
            while (true) {
                // 阻塞
                Socket clientSocket = serverSocket.accept();
                System.out.println(String.format("客户端 %s进行连接", clientSocket.getRemoteSocketAddress()));

                new Thread(() -> {
                    try (InputStream inputStream = clientSocket.getInputStream()){
                        while (true) {
                            byte[] cache = new byte[4096];
                            // 定时发送心跳检测，如果客户端已经关闭连接，那么会抛出异常
                            clientSocket.sendUrgentData(0);
                            inputStream.read(cache);
                            System.out.println(clientSocket.getRemoteSocketAddress() + "发送数据 " + new String(cache));
                        }

                    } catch (IOException e) {
                        if (e instanceof SocketException) {
                            System.out.println(String.format("客户端 %s 连接关闭", clientSocket.getRemoteSocketAddress()));
                            try {
                                clientSocket.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        throw new RuntimeException(e);
                    }
                }).start();
            }

        } catch (IOException e) {
            throw new RuntimeException("IO Error");
        }
    }


    public static void main(String[] args) {
        HelloServer server = new HelloServer();
        server.start(22233);
    }

}
