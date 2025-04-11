package part2.Server.server;

public interface RpcServer {
    void start(int port); //开启服务端监听
    void stop();
}
