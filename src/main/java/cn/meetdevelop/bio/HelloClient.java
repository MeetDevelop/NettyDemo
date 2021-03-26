package cn.meetdevelop.bio;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * description: HelloClient
 * date: 2020/11/24 10:37
 * author: zgy
 * version: 1.0
 */
public class HelloClient {


    public void connect(String host, int port, String message) {
        try (Socket socket = new Socket(host, port)){
            byte[] data = new byte[1024];
            OutputStream outputStream = socket.getOutputStream();
//            InputStream inputStream = socket.getInputStream();
            outputStream.write(message.getBytes());
//            inputStream.read(data);
//            System.out.println(new String(data));

        } catch (IOException e) {
            throw new RuntimeException("连接失败 " + e);
        }
    }

    public static void main(String[] args){
        HelloClient client = new HelloClient();
        for (int i = 0; i < 5; i++) {
            System.out.println(String.format("第%d次建立连接", i));
            client.connect("localhost", 22233, String.format("%d次连接", i));
        }
    }
}
