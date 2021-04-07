package cn.meetdevelop.netty.demo.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * description: FirstServerOutHandler
 * date: 2021/4/7 17:17
 * author: zgy
 * version: 1.0
 */
public class FirstServerOutHandler extends ChannelOutboundHandlerAdapter {

//    @Override
//    public void read(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + " FirstServerOutHandler read.");
//        super.read(ctx);
//    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        System.out.println(new Date() + " FirstServerOutHandler write " + buf.toString(Charset.forName("utf-8")));
    }
}
