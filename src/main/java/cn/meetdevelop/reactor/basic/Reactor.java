package cn.meetdevelop.reactor.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * description: Reactor
 * date: 2020/12/16 9:41
 * author: zgy
 * version: 1.0
 */
public class Reactor implements Runnable {

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;


    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);

        // Reactor 就可以看成一个 Dispatcher
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 附加一个回调对象
        selectionKey.attach(new Acceptor(selector, serverSocketChannel));
    }


    @Override
    public void run() {

        try {
            while (!Thread.interrupted()) {

                // 调用多路复用器来进行轮询
                selector.select();

                // 取得就绪的事件集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> it = selectionKeys.iterator();


                while (it.hasNext()) {
                    // 任务分发
                    dispatch(it.next());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey key) {

        Runnable r = (Runnable) key.attachment();

        if (r != null) {

            long start = System.currentTimeMillis();

            r.run();

            long end = System.currentTimeMillis();

            System.out.println("cost time  ----->  : " + (end - start));

        }

    }


}
