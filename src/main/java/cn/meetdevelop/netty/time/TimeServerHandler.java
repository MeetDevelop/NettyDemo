package cn.meetdevelop.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * description: TimeServerHandler
 * date: 2020/12/21 9:32
 * author: zgy
 * version: 1.0
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 建立连接");
        // 创建 4 个字节容量的缓存
        ByteBuf buffer = ctx.alloc().buffer(4);

        // Time 协议发送的数据为距离 1900-1-1 12:00 的秒数
        buffer.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        // ChannelFuture 表示的是未完成的异步 IO 事件
        ChannelFuture channelFuture = ctx.writeAndFlush(buffer);

        channelFuture.addListener((ChannelFutureListener) future -> {

            assert future == channelFuture;
            ctx.close();
        });



    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
