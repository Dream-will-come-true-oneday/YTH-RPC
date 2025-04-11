package part2.Server;

import part2.Server.provider.ServerProvider;
import part2.Server.server.RpcServer;
import part2.Server.server.impl.ThreadPoolRPCServer;
import part2.common.service.UserService;
import part2.common.service.impl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ServerProvider serverProvider = new ServerProvider();
        serverProvider.provideServerInterface(userService);
        RpcServer rpcServer = new ThreadPoolRPCServer(serverProvider);
        rpcServer.start(8888);
    }
}
