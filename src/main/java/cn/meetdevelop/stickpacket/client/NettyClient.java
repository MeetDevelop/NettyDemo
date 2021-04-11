package cn.meetdevelop.stickpacket.client;

import cn.meetdevelop.stickpacket.client.handler.FirstClientHandler;
import cn.meetdevelop.stickpacket.protocol.request.MessageRequestPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * description: NettyClient
 * date: 2021/4/7 16:04
 * author: zgy
 * version: 1.0
 */
public class NettyClient {
    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
//                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new FirstClientHandler());
//                        ch.pipeline().addLast(new LoginResponseHandler());
//                        ch.pipeline().addLast(new MessageResponseHandler());
//                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        System.out.println(Thread.currentThread().getName());
        bootstrap.connect("localhost", 22233).addListener(future -> {
            if (future.isSuccess()) {
//                System.out.println(Thread.currentThread().getName());
//                Channel channel = ((ChannelFuture) future).channel();
//                startConsoleThread(channel);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                System.out.println("输入消息至服务端 ： ");
                Scanner scanner = new Scanner(System.in);
                String s = scanner.nextLine();
                channel.writeAndFlush(new MessageRequestPacket(s));
            }


        }).start();
    }
}
