package com.Leonardo.RPC.common.serializer.myCode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import com.Leonardo.RPC.common.Message.MessageType;
import com.Leonardo.RPC.common.serializer.mySerializer.Serializer;

import java.util.List;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/6/2 22:24
 * 按照自定义的消息格式解码数据
 */
public class MyDecoder extends ByteToMessageDecoder {
    @Override
    // ctx是Netty的ChannelHandlerContext对象，提供对管道，通道和事件的访问
    // in是ByteBuf对象，用于读取和操作字节数据
    // out是List<Object>对象，用于存储解码后的对象
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        short messageType = in.readShort();
        if (messageType != MessageType.REQUEST.getCode() && messageType != MessageType.RESPONSE.getCode()){
            System.out.println("暂不支持此种类型数据");
        }
        short serializeType = in.readShort();
        if (Serializer.getSerializerByCode(serializeType) == null){
            throw new RuntimeException();
        }

        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Serializer serializer = Serializer.getSerializerByCode(serializeType);
        Object object = serializer.deserialize(bytes, messageType);
        out.add(object);
    }
}
