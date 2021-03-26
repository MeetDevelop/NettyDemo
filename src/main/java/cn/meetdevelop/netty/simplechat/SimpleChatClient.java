package cn.meetdevelop.netty.simplechat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * description: SimpleChatClient
 * date: 2020/12/23 9:50
 * author: zgy
 * version: 1.0
 */
public class SimpleChatClient {

    private final String host;
    private final int port;

    public SimpleChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void run() {
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer());

            Channel channel = bootstrap.connect(host, port).sync().channel();
            System.out.println("连接服务器成功");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

                String line;

                while ((line = in.readLine()) != null) {
                    channel.writeAndFlush(line + "\r\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new SimpleChatClient("localhost", 6789).run();
    }
}
