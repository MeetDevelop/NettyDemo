package cn.meetdevelop.netty.pipeline.protocol.response;

import cn.meetdevelop.netty.pipeline.protocol.Packet;
import cn.meetdevelop.netty.pipeline.protocol.command.Command;
import lombok.Data;

/**
 * Author:zgy
 * Date:2021/4/6
 */
@Data
public class LoginResponsePacket extends Packet {

    private boolean isSuccess;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
