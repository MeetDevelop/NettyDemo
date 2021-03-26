package cn.meetdevelop.zerocopy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * description: DirectBufferTest
 * date: 2020/5/13 15:37
 * author: zgy
 * version: 1.0
 */
public class DirectBufferCopy {

    private static final int _10M = 1024 * 1024 * 10;

    public static void main(String[] args) throws IOException {

        FileInputStream inputStream = new FileInputStream("D:\\ideaIU-2018.3.2.exe");

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\demo.exe");

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(_10M);

        FileChannel inputStreamChannel = inputStream.getChannel();

        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        long currentTimeMillis = System.currentTimeMillis();
        while (true) {

            byteBuffer.clear();

            int num = inputStreamChannel.read(byteBuffer);

            if (num == -1 ) break;
            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);
        }

        System.out.println("拷贝花费的时间为 : " + String.valueOf(System.currentTimeMillis() - currentTimeMillis));

        inputStream.close();
        fileOutputStream.close();
    }

}
