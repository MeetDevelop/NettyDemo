package cn.meetdevelop.netty.pipeline.protocol.response;

import cn.meetdevelop.netty.pipeline.protocol.Packet;
import cn.meetdevelop.netty.pipeline.protocol.command.Command;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:zgy
 * Date:2021/4/6
 */
@Data
@NoArgsConstructor
public class MessageResponsePacket extends Packet {

    private String message;

    public MessageResponsePacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
