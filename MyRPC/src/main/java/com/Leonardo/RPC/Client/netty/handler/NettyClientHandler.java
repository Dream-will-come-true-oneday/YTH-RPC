package com.Leonardo.RPC.Client.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import com.Leonardo.RPC.common.Message.RpcResponse;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/26 17:29
 * @description Netty 客户端处理器，负责处理服务器端返回的 RPC 响应消息
 *              核心功能：接收服务器的 RpcResponse 对象，存储响应结果并管理连接生命周期
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {
        // 定义一个 AttributeKey 作为 Channel 属性的键（用于存储 RPC 响应）
        AttributeKey<RpcResponse> key = AttributeKey.valueOf("RPCResponse");
        // 将服务器返回的 RpcResponse 对象存储到当前 Channel 的属性中（供上层业务获取结果）
        ctx.channel().attr(key).set(rpcResponse);
        // 处理完成后关闭当前连接（短连接模式，减少资源占用）
        ctx.close();

    }

    /**
     * 异常处理：当通道发生异常时触发
     * @param ctx 通道处理上下文
     * @param cause 异常原因
     */
    // 发生异常后关闭连接，避免无效连接占用资源
    // 打印异常堆栈信息，便于调试
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
