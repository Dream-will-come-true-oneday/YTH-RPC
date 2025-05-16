package part3.Server;

import part3.Server.provider.ServerProvider;
import part3.Server.server.RpcServer;
import part3.Server.server.impl.NettyRpcServer;
import part3.common.service.UserService;
import part3.common.service.impl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ServerProvider serverProvider = new ServerProvider("127.0.0.1", 9999);
        serverProvider.provideServerInterface(userService);
        RpcServer nettyRpcServer = new NettyRpcServer(serverProvider);
        nettyRpcServer.start(9999);
    }
}
