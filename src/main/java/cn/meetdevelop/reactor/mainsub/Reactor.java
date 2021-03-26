package cn.meetdevelop.reactor.mainsub;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * description: Reactor
 * date: 2020/12/17 9:36
 * author: zgy
 * version: 1.0
 */
class Reactor implements Runnable {

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;


    Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 绑定一个 Acceptor，用来对任务进行委派
        key.attach(new Acceptor(serverSocketChannel));
    }


    @Override
    public void run() {

        while (!Thread.interrupted()) {
            try {
                int count = selector.select();   // 返回事件发生的描述符数量，并在事件发生之前阻塞
                if (count == 0) {
                    continue;                   // sub 可能通过 wakeup 直接将 main 唤醒，如果没有事件发生，直接重新进入循环
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

                while (keyIterator.hasNext()) {
                    dispatch(keyIterator.next());
                }
                // 避免重复处理
                selectionKeys.clear();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private void dispatch(SelectionKey key) {
        // 当分为 main 和 sub Reactor 后，只可能返回 Acceptor
        Runnable r = (Runnable) key.attachment();

        if (r != null) {
            r.run();
        }
    }
}
