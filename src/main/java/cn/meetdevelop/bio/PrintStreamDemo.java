package cn.meetdevelop.bio;

import java.io.*;

/**
 * description: PrintStreamDemo
 * date: 2020/12/14 10:39
 * author: zgy
 * version: 1.0
 */
public class PrintStreamDemo {

    public static void main(String[] args) throws IOException {
//        OutputStream outputStream = new FileOutputStream("nioTest1.txt");
//
//        PrintStream printStream = new PrintStream(outputStream);
//
//        printStream.print((char) 65);
//        printStream.println(14);
//
//
//        InputStream inputStream = new FileInputStream("nioTest1.txt");
//        byte[] data = new byte[2];
//        int read = inputStream.read(data);
//        while (read != -1) {
//            System.out.println("读取的数量 " + read);
//            for (int i = 0; i < read; i++) {
//                System.out.println(data[i]);
//            }
//            read = inputStream.read(data);
//        }
//        printStream.close();
//        inputStream.close();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[20]);

        System.out.println(byteArrayInputStream.available());
    }
}
