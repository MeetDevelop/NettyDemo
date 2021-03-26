package cn.meetdevelop.reactor.basic;

import java.io.IOException;

/**
 * description: BasicReactorDemo
 * date: 2020/12/16 9:37
 * author: zgy
 * version: 1.0
 */
public class BasicReactorDemo {

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(6789)).start();
    }
}
