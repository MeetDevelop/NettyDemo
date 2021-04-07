package cn.meetdevelop.netty.demo.pipeline.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

/**
 * description: EchoOutboundHandler1
 * date: 2021/4/7 19:47
 * author: zgy
 * version: 1.0
 */
public class EchoOutboundHandler2 extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("进入 EchoOutboundHandler2.write");

        //ctx.writeAndFlush(Unpooled.copiedBuffer("[第一次write中的write]", CharsetUtil.UTF_8));
//        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("在OutboundHandler里测试一下channel().writeAndFlush", CharsetUtil.UTF_8));
        ctx.write(msg);

        System.out.println("退出 EchoOutboundHandler2.write");
    }
}