package cn.meetdevelop.netty.pipeline.util;


import cn.meetdevelop.netty.pipeline.attribute.Attributes;
import io.netty.channel.Channel;

/**
 * Author:zgy
 * Date:2021/4/9
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.LOGIN).get() != null;
    }
}
