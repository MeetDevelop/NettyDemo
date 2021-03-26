package cn.meetdevelop.netty.demo.first;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * description: MyServer
 * date: 2020/12/3 9:38
 * author: zgy
 * version: 1.0
 */
public class MyServer {

    public static void main(String[] args){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInboundHandler() {
                        @Override
                        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

                        }

                        @Override
                        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

                        }

                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {

                        }

                        @Override
                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {

                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

                        }

                        @Override
                        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

                        }

                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

                        }

                        @Override
                        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

                        }

                        @Override
                        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

                        }

                        @Override
                        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(22233).sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }





    }

}
