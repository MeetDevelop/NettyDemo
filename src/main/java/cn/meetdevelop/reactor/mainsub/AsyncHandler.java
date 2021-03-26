package cn.meetdevelop.reactor.mainsub;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description: AsyncHandler
 * date: 2020/12/17 10:38
 * author: zgy
 * version: 1.0
 */
class AsyncHandler implements Runnable {


    private final SocketChannel socketChannel;

    private final SelectionKey selectionKey;


    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer sendBuffer = ByteBuffer.allocate(2048);


    private static final ExecutorService workers = Executors.newFixedThreadPool(4);

    private static final int READ = 1;     // 读取就绪
    private static final int SEND = 2;     // 响应就绪
    private static final int PROCESSING = 4;   // 处理中


    private int status = READ;

    private int num;   // Selector 的序号


    AsyncHandler(SocketChannel socketChannel, SelectionKey selectionKey, int next) throws IOException {
        this.socketChannel = socketChannel;
        this.selectionKey = selectionKey;
        selectionKey.attach(this);
        this.num = next;

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


    private void read() {
        if (selectionKey.isValid()) {
            try {
                readBuffer.clear();

                // read方法结束，意味着本次"读就绪"变为"读完毕"，标记着一次就绪事件的结束
                int count = socketChannel.read(readBuffer);
                if (count > 0) {
                    status = PROCESSING; // 置为处理中
                    workers.execute(this::readWorker); // 异步处理
                } else {
                    selectionKey.cancel();
                    socketChannel.close();
                    System.out.println(String.format("NO %d SubReactor read closed", num));
                }
            } catch (IOException e) {
                System.err.println("处理read业务时发生异常！异常信息：" + e.getMessage());
                selectionKey.cancel();
                try {
                    socketChannel.close();
                } catch (IOException e1) {
                    System.err.println("处理read业务关闭通道时发生异常！异常信息：" + e.getMessage());
                }
            }
        }
    }

    void send() {
        if (selectionKey.isValid()) {
            status = PROCESSING; // 置为执行中
            workers.execute(this::sendWorker); // 异步处理
            selectionKey.interestOps(SelectionKey.OP_READ); // 重新设置为读
        }
    }

    // 读入信息后的业务处理
    private void readWorker() {
        try {

            // 模拟一段耗时操作
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(String.format("NO %d %s -> Server： %s",
                    num, socketChannel.getRemoteAddress(),
                    new String(readBuffer.array())));
        } catch (IOException e) {
            System.err.println("异步处理read业务时发生异常！异常信息：" + e.getMessage());
        }
        status = SEND;
        selectionKey.interestOps(SelectionKey.OP_WRITE); // 注册写事件
        // 设想一下这种情况
        // 因为是异步执行，subReactor 线程重新进入了阻塞状态
        // 此时异步线程执行完该逻辑后，如果不调用 wakeup ，那么 subReactor 线程会一直阻塞，不会立即返回可以执行的写事件描述符
        selectionKey.selector().wakeup(); // 唤醒阻塞在select的线程
    }

    private void sendWorker() {
        try {
            sendBuffer.clear();
            sendBuffer.put(String.format("NO %d SubReactor recived %s from %s", num,
                    new String(readBuffer.array()),
                    socketChannel.getRemoteAddress()).getBytes());
            sendBuffer.flip();

            // write方法结束，意味着本次写就绪变为写完毕，标记着一次事件的结束
            int count = socketChannel.write(sendBuffer);

            if (count < 0) {
                // 同上，write场景下，取到-1，也意味着客户端断开连接
                selectionKey.cancel();
                socketChannel.close();
                System.out.println(String.format("%d SubReactor send closed", num));
            }

            // 没断开连接，则再次切换到读
            status = READ;
        } catch (IOException e) {
            System.err.println("异步处理send业务时发生异常！异常信息：" + e.getMessage());
            selectionKey.cancel();
            try {
                socketChannel.close();
            } catch (IOException e1) {
                System.err.println("异步处理send业务关闭通道时发生异常！异常信息：" + e.getMessage());
            }
        }
    }
}
