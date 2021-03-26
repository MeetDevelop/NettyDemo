package cn.meetdevelop.reactor.mainsub;

import java.io.IOException;

/**
 * description: MainSubReactorDemo
 * date: 2020/12/17 9:30
 * author: zgy
 * version: 1.0
 */
public class MainSubReactorDemo {

    public static void main(String[] args) throws IOException {

        new Thread(new Reactor(6789)).start();
    }
}
