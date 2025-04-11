package part1.Server;

import part1.Server.provider.ServerProvider;
import part1.Server.server.RpcServer;
import part1.Server.server.impl.ThreadPoolRPCServer;
import part1.common.service.UserService;
import part1.common.service.impl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ServerProvider serverProvider = new ServerProvider();
        serverProvider.provideServerInterface(userService);
        RpcServer rpcServer = new ThreadPoolRPCServer(serverProvider);
        rpcServer.start(8888);
    }
}
