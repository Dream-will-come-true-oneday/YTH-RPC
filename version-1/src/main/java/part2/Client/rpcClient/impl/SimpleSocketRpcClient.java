package part2.Client.rpcClient.impl;

import part2.Client.rpcClient.RpcClient;
import part2.common.Message.RpcRequest;
import part2.common.Message.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SimpleSocketRpcClient implements RpcClient {
    private String host;
    private int port;
    public SimpleSocketRpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    @Override
    // 这里负责底层与服务端的通信
    public RpcResponse sendRequest(RpcRequest request) {
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
