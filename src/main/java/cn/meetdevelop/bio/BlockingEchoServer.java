package cn.meetdevelop.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * description: BlockingEchoServer
 * date: 2020/12/14 10:00
 * author: zgy
 * version: 1.0
 */
public class BlockingEchoServer {

    public static int DEFAULT_PORT = 6789;


    public static void main(String[] args) {

        int port;

        try {
            port = Integer.parseInt(args[0]);
        } catch (RuntimeException ex) {
            port = DEFAULT_PORT;
        }

        ServerSocket serverSocket = null;

        try {
            // 绑定服务器端口
            serverSocket = new ServerSocket(port);

            System.out.println("BlockingEchoServer 已启动, 端口: " + port);
        } catch (IOException e) {
            System.out.println("BlockingEchoServer 启动异常，端口: " + port);
            System.out.println(e.getMessage());
        }


        try (
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                BufferedReader in =
                        new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        ) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                System.out.println("Blocking Echo Server -> " + clientSocket.getRemoteSocketAddress() + " : " + inputLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
