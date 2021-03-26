package cn.meetdevelop.netty.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * description: TimeClient
 * date: 2020/12/21 9:46
 * author: zgy
 * version: 1.0
 */
public class TimeClient {

    public static void main(String[] args) {

        String host = "localhost";
        int port = 6789;


        EventLoopGroup worker = new NioEventLoopGroup();


        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });


            // 启动客户端
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            // 等待关闭连接
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }

    }
}
