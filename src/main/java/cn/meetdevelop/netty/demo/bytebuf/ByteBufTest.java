package cn.meetdevelop.netty.demo.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * Author:zgy
 * Date:2021/3/31
 */
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10, 100);

        buf.writeBytes("hello".getBytes());

        System.out.println("readIndex : " + buf.readerIndex());
        System.out.println("writeIndex : " + buf.writerIndex());
        ByteBuf slice = buf.slice();
        ByteBuf duplicate = buf.duplicate();

        buf.skipBytes(1);  // 实验证明调用 skipBytes 函数后 readIndex 会发生变化
        System.out.println("=======skip=======");
        System.out.println("readIndex : " + buf.readerIndex());
        System.out.println("writeIndex : " + buf.writerIndex());
        System.out.println((char) buf.readByte());

        // slice 和 duplicate 调用后得到的新的 byteBuf 都有新的 readIndex 和 writeIndex
        System.out.println("=======slice=======");
        System.out.println("readIndex : " + slice.readerIndex());
        System.out.println("writeIndex : " + slice.writerIndex());

        System.out.println("=======duplicate=======");
        System.out.println("readIndex : " + duplicate.readerIndex());
        System.out.println("writeIndex : " + duplicate.writerIndex());

    }
}
