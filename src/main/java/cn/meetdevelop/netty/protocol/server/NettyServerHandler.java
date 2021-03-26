package cn.meetdevelop.netty.protocol.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * description: NettyServerHandler
 * date: 2020/12/25 9:49
 * author: zgy
 * version: 1.0
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        System.out.println(new Date() + ": 服务端读到数据  -> " + buf.toString(Charset.forName("utf-8")));


        // 回显数据
        System.out.println("服务端 -> 客户端 : " + ctx.channel().remoteAddress());

        ByteBuf send = ctx.alloc().buffer();
        send.writeBytes("你好".getBytes());

        ctx.channel().writeAndFlush(send);
    }
}
