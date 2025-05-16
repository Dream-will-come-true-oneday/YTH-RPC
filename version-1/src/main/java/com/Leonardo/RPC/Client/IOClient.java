package com.Leonardo.RPC.Client;

import com.Leonardo.RPC.common.Message.RpcRequest;
import com.Leonardo.RPC.common.Message.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class IOClient {
    // 这里负责底层与服务端的通信
    public static RpcResponse sendRequest(String host, int port, RpcRequest request) {
        try {
            Socket socket = new Socket(host, port);

            // 将对象序列化发送到服务端
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // 接收并反序列化服务端返回的对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            // 将 RpcRequest 对象序列化
            oos.writeObject(request);
            oos.flush();

            RpcResponse rpcResponse = (RpcResponse) ois.readObject();
            return rpcResponse;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
