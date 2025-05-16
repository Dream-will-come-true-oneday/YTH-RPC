package part1.common.serializer.myCode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import part1.common.Message.MessageType;
import part1.common.Message.RpcRequest;
import part1.common.Message.RpcResponse;
import part1.common.serializer.mySerializer.Serializer;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/6/2 22:24
 *   依次按照自定义的消息格式写入，传入的数据为request或者response
 *   需要持有一个serialize器，负责将传入的对象序列化成字节数组
 */
@AllArgsConstructor
// MessageToByteEncoder是netty专门设计用来实现编码器的抽象类，可以帮助开发者将Java对象编码成字节数据
public class MyEncoder extends MessageToByteEncoder {
    private Serializer serializer;

    // ctx是netty提供的上下文对象，代表管道上下文，包含通道和处理器相关信息
    // msg是需要编码的对象，即RpcRequest或RpcResponse
    // out是输出的字节缓冲区，用于存储编码后的字节数据
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        System.out.println(msg.getClass());
        if (msg instanceof RpcRequest) {
            out.writeShort(MessageType.REQUEST.getCode());
        } else if (msg instanceof RpcResponse) {
            out.writeShort(MessageType.RESPONSE.getCode());
        }
        out.writeShort(serializer.getType());
        byte[] serializeBytes = serializer.serialize(msg);
        out.writeInt(serializeBytes.length);
        out.writeBytes(serializeBytes);
    }
}
