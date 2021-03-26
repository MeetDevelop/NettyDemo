package cn.meetdevelop.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * description: NioTest2
 * date: 2020/5/13 16:20
 * author: zgy
 * version: 1.0
 */
public class NioTest2 {
    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("NioTest2Input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream( "NioTest2Out.txt");

        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel outputStreamChannel = fileOutputStream.getChannel();


        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);


        while (true) {

            byteBuffer.clear();

            // 如果没有 clear，那么 position 和 limit 相等的话，读取时读取数量只会返回 0
            int count = inputStreamChannel.read(byteBuffer);

            System.out.println("read : " + count);

            if (count == -1) break;

            byteBuffer.flip();

            outputStreamChannel.write(byteBuffer);
        }
    }
}
