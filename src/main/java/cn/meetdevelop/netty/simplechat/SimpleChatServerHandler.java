package cn.meetdevelop.netty.simplechat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * description: SimpleChatServerHandler
 * date: 2020/12/22 10:46
 * author: zgy
 * version: 1.0
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {


    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("SimpleChatClient: " + channel.remoteAddress() + " 在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("SimpleChatClient : " + channel.remoteAddress() + "掉线");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();

        System.out.println("SimpleChatClient: " + channel.remoteAddress() + "异常");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();

        // 将新加入客户端的消息广播
        channels.writeAndFlush("[SERVER] - " + channel.remoteAddress() + " 加入\n");
        channels.add(channel);

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channels.writeAndFlush("[SERVER] - " + channel.remoteAddress() + " 离开\n");

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Channel in = ctx.channel();

        for (Channel channel : channels) {
            if (channel != in) {
                channel.writeAndFlush("[" + in.remoteAddress() + "] " + msg + "\n");
            } else {
                channel.writeAndFlush("[you] " + msg + "\n");

            }
        }

    }
}
