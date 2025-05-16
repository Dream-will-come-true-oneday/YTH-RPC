package com.Leonardo.RPC.Client.netty.nettyInitializer;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import com.Leonardo.RPC.Client.netty.handler.NettyClientHandler;
import com.Leonardo.RPC.common.serializer.myCode.MyDecoder;
import com.Leonardo.RPC.common.serializer.myCode.MyEncoder;
import com.Leonardo.RPC.common.serializer.mySerializer.JsonSerializer;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/26 17:26
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyDecoder());
        pipeline.addLast(new MyEncoder(new JsonSerializer()));
        pipeline.addLast(new NettyClientHandler());
    }
}
