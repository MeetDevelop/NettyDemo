package cn.meetdevelop.netty.pipeline.protocol.serialize;

import cn.meetdevelop.netty.pipeline.protocol.serialize.impl.JsonSerializer;

/**
 * 序列化抽象
 * Author:zgy
 * Date:2021/3/31
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();


    /**
     * 使用的序列化算法
     *
     * @return
     */
    byte getSerializerAlogrithm();

    byte[] serialize(Object object);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
