package part2.Client.proxy;

import part2.Client.rpcClient.RpcClient;
import part2.Client.rpcClient.impl.NettyRpcClient;
import part2.Client.rpcClient.impl.SimpleSocketRpcClient;
import part2.common.Message.RpcRequest;
import part2.common.Message.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class ClientProxy implements InvocationHandler {
    private String host;
    private int port;
    private RpcClient rpcClient;

    public ClientProxy(String host, int port, int choose) {
        switch (choose) {
            case 0:
                this.rpcClient = new NettyRpcClient(host, port);
                break;
            case 1:
                this.rpcClient = new SimpleSocketRpcClient(host, port);
        }
    }
    public ClientProxy(String host, int port) {
        rpcClient = new NettyRpcClient(host, port);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsTypes(method.getParameterTypes()).build();
        RpcResponse rpcResponse = rpcClient.sendRequest(rpcRequest);
        return rpcResponse.getData();
    }

    public <T> T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T) o;
    }
}
