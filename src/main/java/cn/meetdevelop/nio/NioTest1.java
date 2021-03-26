package cn.meetdevelop.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * description: NioTest1
 * date: 2020/5/13 16:02
 * author: zgy
 * version: 1.0
 */
public class NioTest1 {
    public static void main(String[] args) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream("NioTest1.txt");

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        byte[] s = "hello world, Netty!".getBytes();

        // 直接将一个 byte 数组存放进 ByteBuffer 中
        byteBuffer.put(s);

        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        byteBuffer.flip();

        while (byteBuffer.hasRemaining()) {
            outputStreamChannel.write(byteBuffer);
        }

        System.out.println("写入结束.....");
        fileOutputStream.close();

    }
}
