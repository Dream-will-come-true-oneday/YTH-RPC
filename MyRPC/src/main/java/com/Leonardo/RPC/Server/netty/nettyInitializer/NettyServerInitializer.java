package com.Leonardo.RPC.Server.netty.nettyInitializer;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;
import com.Leonardo.RPC.Server.netty.handler.NettyRPCServerHandler;
import com.Leonardo.RPC.Server.provider.ServiceProvider;
import com.Leonardo.RPC.common.serializer.myCode.MyDecoder;
import com.Leonardo.RPC.common.serializer.myCode.MyEncoder;
import com.Leonardo.RPC.common.serializer.mySerializer.JsonSerializer;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/26 16:15
 */
@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private ServiceProvider serviceProvider;
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //使用自定义的编/解码器
        pipeline.addLast(new MyEncoder(new JsonSerializer()));
        pipeline.addLast(new MyDecoder());
        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));
    }
}
