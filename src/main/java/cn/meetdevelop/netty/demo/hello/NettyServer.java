package cn.meetdevelop.netty.demo.hello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * description: NettyServer
 * date: 2020/12/14 9:38
 * author: zgy
 * version: 1.0
 */
public class NettyServer {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        ByteBuf buf = Unpooled.buffer(9);

        buf.writeInt(4);

        byte b = buf.readByte();

        ByteBuf slice = buf.slice();
        ByteBuf copy = buf.copy();

        System.out.println("readIndex : " + buf.readerIndex());
        System.out.println("copy readIndex : " + copy.readerIndex());
        System.out.println("readIndex : " + slice.readerIndex());
        System.out.println("writeIndex : " + slice.writerIndex());
        System.out.println("readableBytes : " + slice.readableBytes());
        System.out.println("capacity : " + slice.capacity());
        System.out.println("maxCapacity : " + slice.maxCapacity());

    }
}
