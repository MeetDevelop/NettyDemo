package cn.meetdevelop.netty.pipeline.protocol.serialize.impl;

import cn.meetdevelop.netty.pipeline.protocol.serialize.Serializer;
import cn.meetdevelop.netty.pipeline.protocol.serialize.SerializerAlgorithm;
import com.alibaba.fastjson.JSON;

/**
 * Author:zgy
 * Date:2021/3/31
 */
public class JsonSerializer implements Serializer {


    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlgorithm.Json;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }


}
