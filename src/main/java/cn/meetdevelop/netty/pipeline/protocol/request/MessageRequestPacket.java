package cn.meetdevelop.netty.pipeline.protocol.request;

import cn.meetdevelop.netty.pipeline.protocol.Packet;
import cn.meetdevelop.netty.pipeline.protocol.command.Command;
import lombok.Data;

/**
 * Author:zgy
 * Date:2021/4/6
 */
@Data
public class MessageRequestPacket extends Packet {

    private String message;

    public MessageRequestPacket() {

    }

    public MessageRequestPacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
