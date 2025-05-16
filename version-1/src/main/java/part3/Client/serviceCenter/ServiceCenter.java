package part3.Client.serviceCenter;

import java.net.InetSocketAddress;

public interface ServiceCenter {
    public InetSocketAddress serviceDiscovery(String serviceName);
}
