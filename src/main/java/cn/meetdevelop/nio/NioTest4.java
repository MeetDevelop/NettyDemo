package cn.meetdevelop.nio;

import jdk.management.resource.internal.inst.RandomAccessFileRMHooks;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * description: mmap  直接将文件和内存进行映射，实现零拷贝；减少了一次从 kernel buffer 到 app buffer 的数据拷贝
 * date: 2020/5/13 17:03
 * author: zgy
 * version: 1.0
 */
public class NioTest4 {
    public static void main(String[] args) throws IOException {

        RandomAccessFile file = new RandomAccessFile("NioTest4.txt", "rw");
        FileChannel channel = file.getChannel();


        // 将文件直接与内存进行了映射
        // 其中的 position 代表可以直接修改的起始位置， size 代表映射的大小
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'w');
        mappedByteBuffer.put(2, (byte) 'c');

        file.close();
    }
}
