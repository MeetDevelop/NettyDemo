package cn.meetdevelop.netty.demo.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Author:zgy
 * Date:2021/3/29
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 服务端读到的数据为 -> " + byteBuf.toString(Charset.forName("utf-8")));

        // 回复数据给客户端

        System.out.println(new Date() + " 服务端回复数据");
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes("消息已正确收到".getBytes(Charset.forName("utf-8")));
        ctx.channel().writeAndFlush(buf);
    }
}
