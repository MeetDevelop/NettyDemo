package cn.meetdevelop.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * description: NonBlockingEchoClient
 * date: 2020/12/15 11:26
 * author: zgy
 * version: 1.0
 */
public class NonBlockingEchoClient {


    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("输入格式错误，用法：java NonBlockingEchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        SocketChannel socketChannel;

        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(hostName, portNumber));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ByteBuffer readBuffer = ByteBuffer.allocate(64);
        ByteBuffer writeBuffer = ByteBuffer.allocate(64);

        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;

            while ((userInput = stdIn.readLine()) != null) {

                writeBuffer.put(userInput.getBytes());
                writeBuffer.flip();

                // send message
                socketChannel.write(writeBuffer);

//                socketChannel.read(readBuffer);

                writeBuffer.clear();
//                readBuffer.clear();
                System.out.println("echo : " + userInput);

            }
            socketChannel.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
