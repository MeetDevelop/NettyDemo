package cn.meetdevelop.netty.simplechat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * description: SimpleChatServer
 * date: 2020/12/22 9:59
 * author: zgy
 * version: 1.0
 */
public class SimpleChatServer {

    private int port;

    public SimpleChatServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {

        // 初始化事件循环组
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();


        try {

            // 初始化引导器
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChatServerInitializer());

            ChannelFuture channelFuture = bootstrap.bind(port).sync();

            System.out.println("服务器在端口 : " + port + " 启动...");
            channelFuture.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
            System.out.println("SimpleChatServer 关闭了");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int port;
        try {
            if (args.length > 0) {
                port = Integer.valueOf(args[0]);
            } else {
                port = 6789;
            }
        } catch (RuntimeException e) {
            port = 6789;
        }


        new SimpleChatServer(port).run();
    }

}
