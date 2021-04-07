package cn.meetdevelop.netty.pipeline.server.handler;

import cn.meetdevelop.netty.pipeline.protocol.request.LoginRequestPacket;
import cn.meetdevelop.netty.pipeline.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * Author:zgy
 * Date:2021/4/6
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println(new Date() + " : 收到客户端的登录请求。");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());

        if (valid(msg)) {
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date() + " : 登录成功！");
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码验证失败。。。");
            System.out.println(new Date() + ": 登陆失败！");
        }
    }

    private boolean valid(LoginRequestPacket msg) {
        return true;
    }
}
