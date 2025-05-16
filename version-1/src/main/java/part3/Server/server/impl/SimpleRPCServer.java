package part3.Server.server.impl;

import lombok.AllArgsConstructor;
import part3.Server.provider.ServerProvider;
import part3.Server.server.RpcServer;
import part3.Server.server.work.WorkThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@AllArgsConstructor
public class SimpleRPCServer implements RpcServer {
    private ServerProvider serverProvider;

    @Override
    public void start(int port) {
        try {
            // 创建一个ServerSocket实例，用于在指定的 port 端口上监听客户端的请求
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new WorkThread(socket, serverProvider)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        // 未来优化
    }
}
