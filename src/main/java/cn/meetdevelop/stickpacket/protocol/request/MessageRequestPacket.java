package cn.meetdevelop.stickpacket.protocol.request;

import cn.meetdevelop.stickpacket.protocol.Packet;
import cn.meetdevelop.stickpacket.protocol.command.Command;
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
