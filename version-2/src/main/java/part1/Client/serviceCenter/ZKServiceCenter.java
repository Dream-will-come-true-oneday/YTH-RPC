package part1.Client.serviceCenter;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/5/3 21:41
 */

public class ZKServiceCenter implements ServiceCenter{
    // 使用Curator提供的zookeeper客户端
    private CuratorFramework client;


    // 默认根节点
    private static final String ROOT_PATH = "MyRPC";
    public ZKServiceCenter(){
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        this.client = CuratorFrameworkFactory.builder().connectString("1.12.63.113:2181")
                .namespace(ROOT_PATH).retryPolicy(policy).sessionTimeoutMs(40000).build();
        this.client.start();
    }
    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            List<String> strings = client.getChildren().forPath("/" + serviceName);
            String string = strings.get(0);
            return parseAddress(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String parseString(InetSocketAddress address){
        return address.getHostString() +
                ":"
                + address.getPort();
    }

    private InetSocketAddress parseAddress(String address){
        String[] result = address.split(":");
        return new InetSocketAddress(result[0], Integer.parseInt(result[1]));
    }
}
