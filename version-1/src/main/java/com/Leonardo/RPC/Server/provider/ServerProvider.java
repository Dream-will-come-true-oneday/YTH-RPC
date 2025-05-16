package com.Leonardo.RPC.Server.provider;

import java.util.HashMap;
import java.util.Map;

public class ServerProvider {
    private Map<String, Object> interfaceProvider;
    public ServerProvider(){
        this.interfaceProvider = new HashMap<String, Object>();
    }

    // 本地注册服务
    public void provideServerInterface(Object service){
         Class<?>[] serviceInterfaces = service.getClass().getInterfaces();
         for(Class<?> serviceInterface : serviceInterfaces){
             interfaceProvider.put(serviceInterface.getName(), service);
         }
    }
    // 获取服务实例
    public Object getServer(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
