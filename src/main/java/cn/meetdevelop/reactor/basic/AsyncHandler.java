package cn.meetdevelop.reactor.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sun.deploy.perf.DeployPerfUtil.write;

/**
 * description: AsyncHandler
 * date: 2020/12/16 10:05
 * author: zgy
 * version: 1.0
 */
class AsyncHandler implements Runnable {

    private final Selector selector;

    private final SocketChannel socketChannel;

    private final SelectionKey selectionKey;


    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(2048);


    private static final ExecutorService workers = Executors.newFixedThreadPool(4);

    private static final int READ = 1;     // 读取就绪
    private static final int SEND = 2;     // 响应就绪
    private static final int PROCESSING = 4;   // 处理中


    private int status = READ;

    AsyncHandler(SocketChannel socketChannel, Selector selector) throws IOException {
        this.socketChannel = socketChannel;

        this.socketChannel.configureBlocking(false);
        selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
        selectionKey.attach(this);
        this.selector = selector;
//        this.selector.wakeup();
    }


    @Override
    public void run() {

        switch (status) {

            case READ:
                read();
                break;
            case SEND:
                send();
                break;
            default:
        }
    }

    private void send() {
        if (selectionKey.isValid()) {

            status = PROCESSING;

            workers.execute(this::sendWorker);

            selectionKey.interestOps(SelectionKey.OP_READ);

        }

    }


    private void read() {

        if (selectionKey.isValid()) {

            try {

                readBuffer.clear();

                int count = socketChannel.read(readBuffer);

                if (count > 0) {
                    status = PROCESSING;
                    workers.execute(this::readWorker);
                } else {
                    // 如果客户端连接正常关闭，那么需要将对应的 selectionKey 从集合中剔除
                    // 否则下次依然会被 select 到

                    selectionKey.cancel();
                    socketChannel.close();
                    System.out.println("read closed");
                }
            } catch (IOException e) {
                System.err.println("处理 Read 业务时出现异常! 异常信息 : " + e.getMessage());
                selectionKey.cancel();

                try {
                    socketChannel.close();
                } catch (IOException e1) {
                    System.err.println("处理 Read 业务时出现异常! 异常信息 : " + e1.getMessage());
                }
            }


        }

    }


    // 处理读数据的逻辑
    private void readWorker() {

        System.out.println(String.format("client  ->  Server : %s", new String(readBuffer.array())));
        status = SEND;
        selectionKey.interestOps(SelectionKey.OP_WRITE);
    }


    private void sendWorker() {
        try {

            writeBuffer.clear();
            writeBuffer.put(String.format("received %s from %s", new String(readBuffer.array()), socketChannel.getRemoteAddress()).getBytes());

            writeBuffer.flip();

            socketChannel.write(writeBuffer);

            status = READ;

        } catch (IOException e) {
            System.err.println("异步处理 send 业务时发现异常! 异常信息 : " + e.getMessage());
            selectionKey.cancel();

            try {
                socketChannel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
