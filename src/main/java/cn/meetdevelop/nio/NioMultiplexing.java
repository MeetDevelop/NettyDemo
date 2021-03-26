package cn.meetdevelop.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * description: NioMultiplexing
 * date: 2020/12/1 9:36
 * author: zgy
 * version: 1.0
 */
public class NioMultiplexing {

    private static ByteBuffer recvBuffer = ByteBuffer.allocate(1024);
    private static ByteBuffer sendBuffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(22233));
        serverSocketChannel.configureBlocking(false);
        System.out.println("服务器启动");
        // 选择器
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {

            // 如果满足条件，说明至少有一件事件发生
            if (selector.select() > 0) {

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();


                while (selectionKeyIterator.hasNext()) {

                    SelectionKey selectionKey = selectionKeyIterator.next();

                    // 当有 Accept 事件发生，serverSocketChannel 可以进行处理
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel clientChannel = server.accept();
                        System.out.println(String.format("客户端 %s 建立连接", clientChannel.getRemoteAddress()));
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();

                        // 每一次读取时都要清除上次读取的数据
                        recvBuffer.clear();

                        int len = channel.read(recvBuffer);

                        System.out.println(new String(recvBuffer.array(), 0, len));
                        channel.register(selector, SelectionKey.OP_WRITE);
                    } else if (selectionKey.isWritable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        System.out.println("向客户端 " + channel.getRemoteAddress() + " 发送数据");
                        sendBuffer.clear();

                        sendBuffer.put("连接已经建立".getBytes());
                        sendBuffer.flip();
                        channel.write(sendBuffer);
                        // 这一句话很关键，如果没有，那么 write 事件就会一直触发，死循环
                        channel.register(selector, SelectionKey.OP_READ);
                    }

                    selectionKeyIterator.remove();

                }
            }


        }


    }
}
