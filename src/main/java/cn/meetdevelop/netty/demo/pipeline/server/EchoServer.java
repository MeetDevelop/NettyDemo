package cn.meetdevelop.netty.demo.pipeline.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * description: EchoServer
 * date: 2021/4/7 19:36
 * author: zgy
 * version: 1.0
 */
public class EchoServer {
    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    private void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            //outboundhandler一定要放在最后一个inboundhandler之前
                            //否则outboundhandler将不会执行到
                            ch.pipeline().addLast(new EchoOutboundHandler3());
                            ch.pipeline().addLast(new EchoOutboundHandler2());
                            ch.pipeline().addLast(new EchoOutboundHandler1());

                            ch.pipeline().addLast(new EchoInboundHandler1());
                            ch.pipeline().addLast(new EchoInboundHandler2());
                            ch.pipeline().addLast(new EchoInboundHandler3());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 10000)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            System.out.println("EchoServer正在启动.");

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("EchoServer绑定端口" + port);

            channelFuture.channel().closeFuture().sync();
            System.out.println("EchoServer已关闭.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 22233;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        EchoServer server = new EchoServer(port);
        server.run();
    }
}