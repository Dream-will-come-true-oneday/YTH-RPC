package com.Leonardo.RPC.Server;


import com.Leonardo.RPC.Server.provider.ServiceProvider;
import com.Leonardo.RPC.Server.server.RpcServer;
import com.Leonardo.RPC.Server.server.impl.NettyRPCRPCServer;
import com.Leonardo.RPC.common.service.Impl.UserServiceImpl;
import com.Leonardo.RPC.common.service.UserService;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/11 19:39
 */

public class TestServer {
    public static void main(String[] args) {
        UserService userService=new UserServiceImpl();

        ServiceProvider serviceProvider=new ServiceProvider("127.0.0.1",9999);
        serviceProvider.provideServiceInterface(userService);

        RpcServer rpcServer=new NettyRPCRPCServer(serviceProvider);
        rpcServer.start(9999);
    }
}
