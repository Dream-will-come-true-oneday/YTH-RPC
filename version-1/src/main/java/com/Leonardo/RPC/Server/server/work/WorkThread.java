package com.Leonardo.RPC.Server.server.work;

import lombok.AllArgsConstructor;
import com.Leonardo.RPC.Server.provider.ServerProvider;
import com.Leonardo.RPC.common.Message.RpcRequest;
import com.Leonardo.RPC.common.Message.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

@AllArgsConstructor
public class WorkThread implements Runnable{
    private Socket socket;
    private ServerProvider serverProvider;


    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            RpcRequest rpcRequest = (RpcRequest) ois.readObject();
            RpcResponse rpcResponse = getRpcResponse(rpcRequest);

            oos.writeObject(rpcResponse);
            oos.flush();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private RpcResponse getRpcResponse(RpcRequest rpcRequest){
        // 得到服务名
        String interfaceName = rpcRequest.getInterfaceName();
        // 得到服务端相应的服务实例
        Object service = serverProvider.getServer(interfaceName);

        try {
            // 获得方法对象
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamsTypes());

            Object invoke = method.invoke(service, rpcRequest.getParams());

            return RpcResponse.success(invoke);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
