package cn.meetdevelop.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * description: BlockingEchoClient
 * date: 2020/12/15 9:20
 * author: zgy
 * version: 1.0
 */
public class BlockingEchoClient {


    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("输入格式错误，用法：java BlockingClient <host name> <port number>");
            System.exit(1);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try (
                Socket echoSocket = new Socket(host, port);

                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);

                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ) {

            String userInput;

            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo : " + userInput);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
