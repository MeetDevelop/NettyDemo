package cn.meetdevelop.stickpacket.protocol.response;

import cn.meetdevelop.stickpacket.protocol.Packet;
import cn.meetdevelop.stickpacket.protocol.command.Command;
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
