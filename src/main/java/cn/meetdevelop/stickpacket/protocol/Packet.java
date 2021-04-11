package cn.meetdevelop.stickpacket.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * description: Packet
 * date: 2020/12/25 10:04
 * author: zgy
 * version: 1.0
 */
@Data
public abstract class Packet {

    // JsonField 是 FastJson 中的一个注解，其主要是用来指定对象中域的属性
    // serialize：表示当前域是否会被序列化；如果设置为 false 那么表示当前域不会被序列化，
    // 因此将对象转换成 String 时，不会包含该域
    //deserialize: 表示当反序列化时是否将该域进行反序列化；如果其设置为 false，就算转换的 String
    // 中该域有值，转换成对象后该域也会被设置成 null
    // 当将 JsonField 设置到 get 、 set 方法时；get 主要是对序列化有效，即不管是否有该域，只要有 get
    // 方法就可以将该域转换为 Json String；Set 方法则主要是对反序列化起作用
    @JSONField(serialize = false)
    private Byte version = 1;

    @JSONField(serialize = false)
    public abstract Byte getCommand();

//    public static void main(String[] args) {
//        Packet packet = new Packet();
//        packet.setVersion((byte) 2);
//        String s = JSONObject.toJSONString(packet);
//        System.out.println(s);
//
//        Packet packet1 = JSONObject.parseObject(s, Packet.class);
//        System.out.println(packet1);
//    }

}
