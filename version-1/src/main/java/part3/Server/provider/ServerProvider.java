package part3.Server.provider;

import part3.Server.serviceRegister.Impl.ZKServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ServerProvider {

    private int port;
    private String host;
    private Map<String, Object> interfaceProvider;
    private ZKServiceRegister serviceRegister;

    public ServerProvider(String host, int port){
        this.host = host;
        this.port = port;
        this.interfaceProvider = new HashMap<String, Object>();
        this.serviceRegister = new ZKServiceRegister();
    }

    // 注册服务
    public void provideServerInterface(Object service){
         String serviceName = service.getClass().getName();
         Class<?>[] serviceInterfaces = service.getClass().getInterfaces();

         for(Class<?> serviceInterface : serviceInterfaces){
             interfaceProvider.put(serviceInterface.getName(), service);
             serviceRegister.register(serviceInterface.getName(), new InetSocketAddress(host, port));
         }
    }
    // 获取服务实例
    public Object getServer(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
