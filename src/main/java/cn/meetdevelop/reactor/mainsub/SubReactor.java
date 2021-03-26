package cn.meetdevelop.reactor.mainsub;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * description: SubReactor
 * date: 2020/12/17 9:58
 * author: zgy
 * version: 1.0
 */
public class SubReactor implements Runnable {

    private Selector selector;
    private boolean register = false;   // 注册开关表示
    private int num;    // 序号，Acceptor 初始化 subReactor 时的序号

    public SubReactor(Selector selector, int num) {
        this.selector = selector;
        this.num = num;
    }


    @Override
    public void run() {

        while (!Thread.interrupted()) {

            System.out.println(String.format("Num %d SubReactor waiting for register...", num));

            while (!Thread.interrupted() && !register) {

                try {
                    int count = selector.select();
                    if (count == 0) {
                        continue;
                    }

                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

                    while (keyIterator.hasNext()) {
                        dispatch(keyIterator.next());
                        keyIterator.remove();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private void dispatch(SelectionKey key) {
        Runnable r = (Runnable) key.attachment();

        if (r != null) {
            r.run();

        }
    }

    public void registering(boolean register) {
        this.register = register;
    }
}
