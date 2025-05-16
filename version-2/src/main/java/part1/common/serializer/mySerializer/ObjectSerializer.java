package part1.common.serializer.mySerializer;

import java.io.*;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/6/2 22:36
 */
public class ObjectSerializer implements Serializer {
    //利用Java io 对象 -》字节数组
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes = null;
        // 创建一个内存中的输出流，用于存储序列化后的字节数据
        // ByteArrayOutputStream 是一个字节数组输出流，它将数据写入一个内部缓冲区，
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // 创建一个对象输出流，用于将对象写入输出流中
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            // 将对象写入对象输出流中
            oos.writeObject(obj);
            // 刷新输出流，确保所有数据都被写入底层字节数组中
            oos.flush();
            // 获取缓冲区的字节数组
            bytes = bos.toByteArray();
            bos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    //字节数组 -》对象
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        try {
            // 把字节数组包装成一个输入流
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            // 创建一个对象输入流，用于从输入流中读取对象
            ObjectInputStream ois = new ObjectInputStream(bis);
            // 从bis中读取序列化的对象，并将其反序列化成Java对象
            obj = ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    //0 代表Java 原生序列器
    @Override
    public int getType() {
        return 0;
    }
}

