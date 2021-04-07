package cn.meetdevelop.netty.pipeline.server.handler;

import cn.meetdevelop.netty.pipeline.protocol.request.MessageRequestPacket;
import cn.meetdevelop.netty.pipeline.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * Author:zgy
 * Date:2021/4/6
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();

        System.out.println(new Date() + " : 收到的客户端消息为： " + msg.getMessage());
        messageResponsePacket.setMessage("服务端回复【" + msg.getMessage() + "】");
    }
}
