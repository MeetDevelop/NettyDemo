package cn.meetdevelop.nio;

import java.nio.ByteBuffer;

/**
 * description: Slice 方法使用，slice 方法调用后生成的 Buffer 和原 Buffer 底层使用同一数组
 * date: 2020/5/13 16:36
 * author: zgy
 * version: 1.0
 */

public class NioTest3 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }

        byteBuffer.position(2);

        byteBuffer.limit(6);

        // 进行 slice 操作
        ByteBuffer sliceBuffer = byteBuffer.slice();

        // 修改 sliceBuffer 中的值
        for (int i = 0; i < sliceBuffer.capacity(); i++) {

            byte b = sliceBuffer.get();
            sliceBuffer.put(i, (byte) (b * 2));
        }

        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());

        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }


    }
}
