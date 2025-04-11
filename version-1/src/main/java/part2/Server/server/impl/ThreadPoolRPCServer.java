package part2.Server.server.impl;

import part2.Server.provider.ServerProvider;
import part2.Server.server.RpcServer;
import part2.Server.server.work.WorkThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRPCServer implements RpcServer {
    private ServerProvider serverProvider;
    private final ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolRPCServer(ServerProvider serverProvider) {
        this.serverProvider = serverProvider;
        // 核心线程数等于CPU核数，最大线程数为1000，非核心线程空闲存活时间为60秒，队列容量为100
        this.threadPoolExecutor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                1000, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100)
        );
    }

    public ThreadPoolRPCServer(ServerProvider serverProvider, int corePoolSize
    , int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this.serverProvider = serverProvider;
        this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize, keepAliveTime, unit, workQueue);
    }
    @Override
    public void start(int port) {
        System.out.println("服务启动了");

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                threadPoolExecutor.execute(new WorkThread(socket, serverProvider));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {

    }
}
