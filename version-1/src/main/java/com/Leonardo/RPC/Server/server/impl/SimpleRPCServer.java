package com.Leonardo.RPC.Server.server.impl;

import lombok.AllArgsConstructor;
import com.Leonardo.RPC.Server.provider.ServerProvider;
import com.Leonardo.RPC.Server.server.RpcServer;
import com.Leonardo.RPC.Server.server.work.WorkThread;

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
