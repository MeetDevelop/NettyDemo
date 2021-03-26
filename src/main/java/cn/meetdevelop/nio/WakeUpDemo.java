package cn.meetdevelop.nio;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * description: WakeUpDemo
 * date: 2020/12/16 21:12
 * author: zgy
 * version: 1.0
 */
public class WakeUpDemo {
    public static void main(String[] args) throws IOException, InterruptedException {

        Selector selector = Selector.open();
        new Thread(() -> {
            try {
                System.out.println("hello");
                selector.select();
                System.out.println("world");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(5000);
        System.out.println("Start");
        Thread.sleep(5000);

        selector.wakeup();
    }
}
