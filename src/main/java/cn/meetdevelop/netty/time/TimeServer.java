package cn.meetdevelop.netty.time;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * description: TimeServer
 * date: 2020/12/21 9:27
 * author: zgy
 * version: 1.0
 */
public class TimeServer {


    public static void main(String[] args) throws InterruptedException {

        int port;

        try {
            port = Integer.parseInt(args[0]);
        } catch (RuntimeException e) {
            port = 6789;
        }


        // 初始化 EventLoopGroup
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        // 初始化引导器
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new TimeServerHandler());
                    }
                });

        // 绑定端口，启动服务器
        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

        System.out.println("服务器已经启动 -> " + port);

        // 等待服务器的 Socket 关闭
        channelFuture.channel().closeFuture().sync();



    }
}
