package part2.Client.netty.nettyInitializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import part2.Client.netty.handler.NettyClientHandler;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        /*
        * 用于基于长度字段的帧解码器，它会自动处理拆包和粘包问题
        * 第一个参数：最大帧长度，即单个帧的最大字节数，这里设置为 Integer.MAX_VALUE，表示不限制帧的大小
        * 具体地，它根据消息头中包含的长度字段来解析消息体*/
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

        // LengthFieldPrepender 用于在发送数据前，自动计算消息体的长度并将其添加到消息的前面中
        pipeline.addLast(new LengthFieldPrepender(4));
        // 将Java对象转换为字节流进行传输
        pipeline.addLast(new ObjectEncoder());

        // ObjectDecoder是一个解码器，根据收到的类名解析出相应的Java类
        pipeline.addLast(new ObjectDecoder(Class::forName));

        pipeline.addLast(new NettyClientHandler());
    }
}
