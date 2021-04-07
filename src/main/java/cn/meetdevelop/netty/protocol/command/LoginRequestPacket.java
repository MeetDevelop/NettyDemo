package cn.meetdevelop.netty.protocol.command;


import lombok.Data;

/**
 * Author:zgy
 * Date:2021/3/31
 */
@Data
public class LoginRequestPacket extends Packet {
    private Integer userId;

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

//    public static void main(String[] args) {
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setVersion((byte) 10);
//        loginRequestPacket.setUserId(1);
//        loginRequestPacket.setUserName("zgy");
//        loginRequestPacket.setPassword("123");
//
//
//        System.out.println(JSONObject.toJSONString(loginRequestPacket));
//    }
}
