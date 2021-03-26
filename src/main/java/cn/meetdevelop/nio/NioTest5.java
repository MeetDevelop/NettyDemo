package cn.meetdevelop.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * description: Scattering 与 Gathering
 * Scatting 将数据写入到 buffer 时，可以采用 buffer 数组
 * Gathering 从 buffer 读取数据时，可以采用 buffer 数组
 * date: 2020/5/13 17:25
 * author: zgy
 * version: 1.0
 */
public class NioTest5 {
    public static void main(String[] args) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest5.txt", "rw");

        FileChannel outPutStreamChannel = new FileOutputStream("NioTest5Out.txt").getChannel();

        FileChannel randomAccessFileChannel = randomAccessFile.getChannel();

        // 生成三个 byteBuffer
        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(10);

        long count = randomAccessFileChannel.read(byteBuffers);

        System.out.println("读取的字节数量为 ：" + count);

        Arrays.stream(byteBuffers).
                map(buffer -> "position: " + buffer.position() + ", limit: " + buffer.limit()).
                forEach(System.out::println);

        // 将其写入到指定的 channel 中

        Arrays.stream(byteBuffers).forEach(Buffer::flip);


        outPutStreamChannel.write(byteBuffers);

        outPutStreamChannel.close();
        randomAccessFile.close();
    }
}
