package rpc.common.serializer.mySerializer;


/**
 * @author wxx
 * @version 1.0
 * @create 2024/6/2 22:31
 */
public interface Serializer {
    byte[] serialize(Object obj);
    Object deserialize(byte[] bytes, int messageType);
    int getType();

    static Serializer getSerializerByCode(int code) {
        switch (code) {
            case 0:
                return new ObjectSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
