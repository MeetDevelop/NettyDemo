package cn.meetdevelop.netty.demo.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * description: SecondServerHandler
 * date: 2021/4/7 16:36
 * author: zgy
 * version: 1.0
 */
public class SecondServerHandler extends ChannelInboundHandlerAdapter {

    // 通过 debug 可以证明对于 Registered 方法的执行是将一个 channel 对应的 pipeline 中的所有 handler 都执行完毕后，才会去执行下面的 read 方法
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + " : Second ServerHandler Registered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        System.out.println(new Date() + " SecondServerHandler "+buf.toString(Charset.forName("utf-8")));

        ByteBuf out = ctx.alloc().buffer();
        out.writeBytes("SecondServerHandler write...".getBytes());
        ctx.channel().writeAndFlush(out);
    }
}
