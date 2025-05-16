package com.Leonardo.RPC.Server;

import com.Leonardo.RPC.Server.provider.ServerProvider;
import com.Leonardo.RPC.Server.server.RpcServer;
import com.Leonardo.RPC.Server.server.impl.ThreadPoolRPCServer;
import com.Leonardo.RPC.common.service.UserService;
import com.Leonardo.RPC.common.service.impl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ServerProvider serverProvider = new ServerProvider();
        serverProvider.provideServerInterface(userService);
        RpcServer rpcServer = new ThreadPoolRPCServer(serverProvider);
        rpcServer.start(8888);
    }
}
