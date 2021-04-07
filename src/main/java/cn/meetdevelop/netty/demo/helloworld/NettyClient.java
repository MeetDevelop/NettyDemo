package cn.meetdevelop.netty.demo.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Author:zgy
 * Date:2021/3/29
 */
public class NettyClient {

    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new FirstClientHandler());
                    }
                });
        connect(bootstrap, "localhost", 22233, 5);
        System.out.println(Thread.currentThread().getName());
    }


    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("客户端连接成功");
            } else if (retry == 0) {
                System.out.println(new Date() + " 客户端重连失败");
                bootstrap.config().group().shutdownGracefully();
            } else {
                int order = (MAX_RETRY - retry) + 1;  // 重连的顺序

                int time = 1 << order;   // 本次重连等待的时间
                System.out.println(new Date() + Thread.currentThread().getName());

                System.out.println(new Date() + " 第 " + order + " 次重连...");

                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), time, TimeUnit.SECONDS);


            }

        });
    }


}
