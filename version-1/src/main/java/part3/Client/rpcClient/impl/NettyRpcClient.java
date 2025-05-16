package part3.Client.rpcClient.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import part3.Client.netty.nettyInitializer.NettyClientInitializer;
import part3.Client.rpcClient.RpcClient;
import part3.Client.serviceCenter.ServiceCenter;
import part3.Client.serviceCenter.ZKServiceCenter;
import part3.common.Message.RpcRequest;
import part3.common.Message.RpcResponse;

import java.net.InetSocketAddress;


public class NettyRpcClient implements RpcClient {

    private ServiceCenter serviceCenter;
    private static final Bootstrap bootstrap;
    private static final EventLoopGroup eventLoopGroup;

    public NettyRpcClient() {
        this.serviceCenter = new ZKServiceCenter();
    }
    static {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }

    @Override
    public RpcResponse sendRequest(RpcRequest request) {

        // 从注册中心获取host, Port
        InetSocketAddress address = serviceCenter.serviceDiscovery(request.getInterfaceName());
        String host = address.getHostName();
        int port = address.getPort();
        try {
            // 创建一个channelFuture对象，代表这一个操作事件，sync方法表示堵塞知道connect完成
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            // channel 表示一个连接的单位，类似socket
            Channel channel = channelFuture.channel();

            channel.writeAndFlush(request);
            /* sync()堵塞获取结果
            * 这一行的目的是 阻塞当前线程，直到连接关闭
            * 从而确保服务器已经处理完请求并返回响应（假设响应是通过关闭连接触发的）
            * */
            channel.closeFuture().sync();
            // 阻塞的获得结果，通过给channel设计别名，获取特定名字下的channel中的内容
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("RPCResponse");
            RpcResponse response = channel.attr(key).get();

            System.out.println(response);
            return response;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
