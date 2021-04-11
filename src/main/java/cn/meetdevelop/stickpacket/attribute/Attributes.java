package cn.meetdevelop.stickpacket.attribute;

import io.netty.util.AttributeKey;

/**
 * Author:zgy
 * Date:2021/4/9
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
