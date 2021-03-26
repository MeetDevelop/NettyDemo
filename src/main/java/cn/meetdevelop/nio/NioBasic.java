package cn.meetdevelop.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * description: NioBasic
 * date: 2020/11/30 9:39
 * author: zgy
 * version: 1.0
 */
public class NioBasic {


    public static void main(String[] args) throws IOException, InterruptedException {
        LinkedList<SocketChannel> socketChannels = new LinkedList<>();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(22233));
        serverSocketChannel.configureBlocking(false);

        while (true) {
            Thread.sleep(1000);
            // 设置成了非阻塞，调用函数后会立即返回  socketChannel 为 null 表示没有 socket 连接
            SocketChannel socketChannel = serverSocketChannel.accept();


            if (socketChannel == null ) {
                System.out.println("等待连接...");
            } else {
                socketChannels.add(socketChannel);
                socketChannel.configureBlocking(false);
            }



            // 遍历所有的连接 socket，判断是否有 socket 满足读的条件
            for (SocketChannel channel : socketChannels) {
                try {
                    channel.socket().sendUrgentData(0);

                    System.out.println("连接的数量 : " + socketChannels.size());
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = channel.read(buffer);

                    if (len != -1) {
                        System.out.println(new String(buffer.array()));
                    }
                } catch (SocketException e) {
                    System.out.println(channel.getRemoteAddress() + " 连接关闭...");
                    socketChannels.remove(channel);
                }

            }

        }

    }
}
