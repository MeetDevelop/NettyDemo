package cn.meetdevelop.netty.pipeline.client.handler;

import cn.meetdevelop.netty.pipeline.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * description: LoginResponseHandler
 * date: 2021/4/7 16:12
 * author: zgy
 * version: 1.0
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {

    }
}
