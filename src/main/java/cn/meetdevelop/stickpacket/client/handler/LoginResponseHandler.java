package cn.meetdevelop.stickpacket.client.handler;

import cn.meetdevelop.stickpacket.protocol.request.LoginRequestPacket;
import cn.meetdevelop.stickpacket.protocol.response.LoginResponsePacket;
import cn.meetdevelop.stickpacket.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * description: LoginResponseHandler
 * date: 2021/4/7 16:12
 * author: zgy
 * version: 1.0
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 当客户端与服务器的三次握手建立后，客户端发送登录请求
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(47);
        loginRequestPacket.setUserName("zgy");
        loginRequestPacket.setPassword("hello");

        // 发送请求
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println(new Date() + " 客户端登录成功");
            LoginUtil.markAsLogin(ctx.channel());

        } else {
            System.out.println(new Date() + " : 客户端登录失败，原因：" + msg.getReason());
        }
    }
}
