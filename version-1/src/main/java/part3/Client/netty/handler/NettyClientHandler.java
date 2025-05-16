package part3.Client.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import com.Leonardo.RPC.common.Message.RpcResponse;

public class NettyClientHandler  extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    // ChannelHandlerContext 是 Netty 框架提供的一个上下文对象，用于在 Channel 生命周期内传递数据和状态信息
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {
        // 创建一个AttributeKey对象，用于存储RpcResponse对象
        AttributeKey<RpcResponse> key = AttributeKey.valueOf("RpcResponse");
        // 将服务器返回的RpcResponse对象存储到当前Channel上
        ctx.channel().attr(key).set(rpcResponse);
        // 在接收到响应后，主动关闭连接，采用短连接模式
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
