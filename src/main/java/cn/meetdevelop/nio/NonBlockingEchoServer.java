package cn.meetdevelop.nio;

import com.sun.security.ntlm.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * description: NonBlockingEchoServer
 * date: 2020/12/15 10:09
 * author: zgy
 * version: 1.0
 */
public class NonBlockingEchoServer {
    public static int DEFALUT_PORT = 6789;

    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (RuntimeException e) {
            port = DEFALUT_PORT;
        }

        ServerSocketChannel serverSocketChannel;
        Selector selector;

        // 初始化的配置操作
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("服务器已经启动 -> 端口 : " + port);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        while (true) {

            try {
                selector.select();
            } catch (IOException e) {
                System.out.println("NonBlockingEchoServer 异常 " + e.getMessage());
            }


            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = keys.iterator();

            while (selectionKeyIterator.hasNext()) {

                SelectionKey key = selectionKeyIterator.next();

                try {
                    // 有新连接建立
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();

                        System.out.println("远端客户端 -> " + client.getRemoteAddress() + " 建立连接.");

                        // 将新建立的连接设置为非阻塞
                        client.configureBlocking(false);

                        // 加入到 selector 进行监听
                        SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ);

                        ByteBuffer buffer = ByteBuffer.allocate(128);

                        clientKey.attach(buffer);
                    }


                    // 可读

                    if (key.isReadable()) {

                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();


                        int count = socketChannel.read(output);

                        if (count < 0) {
                            socketChannel.close();
                        }
                        System.out.println("客户端 " + socketChannel.getRemoteAddress() + " 发送过来的消息为 " + output.toString());

                        key.interestOps(SelectionKey.OP_WRITE);
                    }


                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();

                        ByteBuffer output = (ByteBuffer) key.attachment();

                        output.flip();

                        int count = client.write(output);

                        System.out.println("NonBlockingEchoServer  -> " + client.getRemoteAddress() + ": " + output.toString());

                        // 对 Buffer 进行一个清理操作，使其可以重新进行写操作
                        output.compact();

                        key.interestOps(SelectionKey.OP_READ);
                    }
                    selectionKeyIterator.remove();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }

    }
}
