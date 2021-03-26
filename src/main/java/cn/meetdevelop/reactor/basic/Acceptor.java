package cn.meetdevelop.reactor.basic;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * description: Acceptor
 * date: 2020/12/16 9:55
 * author: zgy
 * version: 1.0
 */
public class Acceptor implements Runnable {

    private final Selector selector;

    private final ServerSocketChannel serverSocketChannel;


    Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }


    @Override
    public void run() {
        SocketChannel socketChannel;

        try {
            socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                System.out.println(String.format("接受客户端 %s 的远程连接..", socketChannel.getRemoteAddress()));

                // 将接受的客户端通道传递给 Handler，Handler 负责接下来的事件处理
                new AsyncHandler(socketChannel, selector);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
