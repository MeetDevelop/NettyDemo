package cn.meetdevelop.netty.demo.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Author:zgy
 * Date:2021/3/29
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + " 客户端开始发送数据");


        ByteBuf byteBuf = ctx.alloc().buffer();

        byteBuf.writeBytes("你好，这是客户端的第一次尝试!".getBytes(StandardCharsets.UTF_8));

        // 写数据
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + " 客户端接收到服务端的消息为：-> " + byteBuf.toString(Charset.forName("utf-8")));
    }
}
