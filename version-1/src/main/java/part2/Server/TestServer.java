package part2.Server;

import part2.Server.provider.ServerProvider;
import part2.Server.server.impl.NettyRpcServer;
import part2.common.service.UserService;
import part2.common.service.impl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ServerProvider serverProvider = new ServerProvider();
        serverProvider.provideServerInterface(userService);
        NettyRpcServer nettyRpcServer = new NettyRpcServer(serverProvider);
        nettyRpcServer.start(8888);
    }
}
