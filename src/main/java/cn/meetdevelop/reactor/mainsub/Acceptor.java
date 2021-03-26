package cn.meetdevelop.reactor.mainsub;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * description: Acceptor
 * date: 2020/12/17 9:40
 * author: zgy
 * version: 1.0
 */
public class Acceptor implements Runnable {

    private final ServerSocketChannel serverSocketChannel;

    private final int coreNum = Runtime.getRuntime().availableProcessors();   // 获得当前运行机器上核心数量

    private final Selector[] selectors = new Selector[coreNum];   // subReactor 用来对读、写事件进行监听

    private int next = 0;   // 轮询使用 subReactor 的索引

    private SubReactor[] reactors = new SubReactor[coreNum];

    private Thread[] threads = new Thread[coreNum];   // subReactor 的处理线程

    public Acceptor(ServerSocketChannel serverSocketChannel) throws IOException {
        this.serverSocketChannel = serverSocketChannel;

        for (int i = 0; i < coreNum; i++) {
            selectors[i] = Selector.open();
            reactors[i] = new SubReactor(selectors[i], i);
            threads[i] = new Thread(reactors[i]);   // 初始化运行 subReactor 的线程
            threads[i].start();    // 相当于此时 subReactor 开始对 Channel 的读写事件来进行了监听
        }

    }

    @Override
    public void run() {

        SocketChannel socketChannel;

        try {
            socketChannel = serverSocketChannel.accept();

            if (socketChannel != null) {

                System.out.println(String.format("accept  %s", socketChannel.getRemoteAddress()));

                socketChannel.configureBlocking(false);

                // 如果下面直接调用注册方法，那么会注册失败
                // 在构造 Acceptor 时，subReactor 就会在 select 阻塞，而阻塞时是不能够对事件进行注册的
                reactors[next].registering(true);

                selectors[next].wakeup();    // 使一个阻塞的线程立即返回

                SelectionKey key = socketChannel.register(selectors[next], SelectionKey.OP_READ);


                // 事件注册完成后，再次触发 select 的执行
                reactors[next].registering(false);


                // 绑定 Handler，每一个 SocketChannel 都会绑定一个自己的 Handler
                key.attach(new AsyncHandler(socketChannel, key, next));

                if (++next == selectors.length) {
                    next = 0;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
