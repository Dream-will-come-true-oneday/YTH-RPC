package part3.Client.serviceCenter;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.net.InetSocketAddress;
import java.util.List;

public class ZKServiceCenter implements ServiceCenter {

    private CuratorFramework client;
    // zookeeper 根路径节点
    private static final String ROOT_PATH = "MyRPC";

    public ZKServiceCenter() {
        // 指数时间重试
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        // zookeeper的地址固定，不管是服务提供者还是消费者，都要与之建立连接
        this.client = CuratorFrameworkFactory.builder().connectString("8.138.93.90:2181")
                .sessionTimeoutMs(40 * 1000).retryPolicy(policy).namespace(ROOT_PATH).build();
        this.client.start();
        System.out.println("zookeeper 连接成功");
    }

    // 根据服务名（接口名）返回地址
    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            // 获取服务名对应路径下的所有子节点，，子节点通常保存服务实例的地址（ip:port 格式）
            List<String> strings = client.getChildren().forPath("/" + serviceName);
            // 这里默认第一个，后面加负载均衡
            String string = strings.get(0);
            // 将子节点字符串（ip:port 格式） 解析为InetSocketAddress，便于客户端进行通信
            return parseAddress(string);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 地址 -> XXX.XXX.XXX.XXX:port 字符串
    private String getServiceAddress(InetSocketAddress ServerAddress) {
        return ServerAddress.getHostName() + ":" + ServerAddress.getPort();
    }

    // 将字符串解析为地址
    private InetSocketAddress parseAddress(String string) {
        String[] result = string.split(":");
        return new InetSocketAddress(result[0], Integer.parseInt(result[1]));
    }
}
