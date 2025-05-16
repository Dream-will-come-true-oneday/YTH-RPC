package part1.Client.rpcClient.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.AttributeKey;
import part1.Client.netty.handler.NettyClientHandler;
import part1.Client.rpcClient.RpcClient;
import part1.Client.serviceCenter.ServiceCenter;
import part1.Client.serviceCenter.ZKServiceCenter;
import part1.common.Message.RpcRequest;
import part1.common.Message.RpcResponse;

import java.net.InetSocketAddress;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/5/2 19:40
 */
public class NettyRpcClient implements RpcClient {
    private static Bootstrap bootstrap;
    private static NioEventLoopGroup group;

    // 服务中心实例，用于注册和获取服务
    private ServiceCenter serviceCenter;
    public NettyRpcClient() {
        serviceCenter = new ZKServiceCenter();
    }
    static {
        // 初始化 Netty 客户端，配置 Bootstrap 和 EventLoopGroup
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .handler(new NettyClientHandler());
    }

    @Override
    public RpcResponse sendRequest(RpcRequest request) {
        try {
            // 从服务中心获取服务提供者地址
            InetSocketAddress serviceAddress = serviceCenter.serviceDiscovery(request.getInterfaceName());
            // 与服务端建立连接并发送请求
            /*
            * bootstrap.connect(host, port) 会异步发起与服务器的连接请求
            * ，返回的 ChannelFuture 代表这个连接操作的“未来结果”。
            * */
            ChannelFuture channelFuture = bootstrap.connect(serviceAddress.getAddress(), serviceAddress.getPort()).sync();
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(request);
            // 阻塞直到连接关闭（通常是服务端主动关闭连接），确保接收到完整响应结果
            channel.closeFuture().sync();
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("RpcResponse");
            return channel.attr(key).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
