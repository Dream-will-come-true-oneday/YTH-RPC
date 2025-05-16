package com.Leonardo.RPC.Server.server;

public interface RpcServer {
    void start(int port); //开启服务端监听
    void stop();
}
