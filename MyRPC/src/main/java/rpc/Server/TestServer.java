package rpc.Server;


import rpc.Server.provider.ServiceProvider;
import rpc.Server.server.RpcServer;
import rpc.Server.server.impl.NettyRPCRPCServer;
import rpc.common.service.Impl.UserServiceImpl;
import rpc.common.service.UserService;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/11 19:39
 */

public class TestServer {
    public static void main(String[] args) throws InterruptedException {
        UserService userService=new UserServiceImpl();

        ServiceProvider serviceProvider=new ServiceProvider("127.0.0.1",9999);

        serviceProvider.provideServiceInterface(userService);

        RpcServer rpcServer=new NettyRPCRPCServer(serviceProvider);
        rpcServer.start(9999);
    }
}
