package zookeeper.test;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JunitTest {

    private static CuratorFramework client;

    private static final String ROOT_PATH = "taohua";

    @Before
    public void connect() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        this.client = CuratorFrameworkFactory.builder().connectString("8.138.93.90:2181")
                .sessionTimeoutMs(40000).retryPolicy(retryPolicy).namespace(ROOT_PATH).build();
        client.start();
    }

    @Test
    public void test1() throws Exception {
        byte[] path = client.getData().forPath("/yang");
        System.out.println(new String(path));
    }

    @After
    public void close() {
        if (client != null) {
            client.close();
        }
    }
}
