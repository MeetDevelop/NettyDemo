package cn.meetdevelop.netty.pipeline.codec;

import cn.meetdevelop.netty.pipeline.protocol.Packet;
import cn.meetdevelop.netty.pipeline.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Author:zgy
 * Date:2021/4/6
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodeC.INSTANCE.encode(out, msg);
    }
}
