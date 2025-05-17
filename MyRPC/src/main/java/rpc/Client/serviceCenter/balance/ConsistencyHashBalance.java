package rpc.Client.serviceCenter.balance;

import java.util.*;

public class ConsistencyHashBalance implements LoadBalance{
    // 虚拟节点个数
    private static final int VIRTUAL_NUM = 5;
    // 存放虚拟节点的hash值到真实节点的映射，key是虚拟节点的hash值，value是真实节点的名称
    private SortedMap<Integer, String> shards = new TreeMap<Integer, String>();
    // 存放真实节点，便于增删节点时查询节点是否已存在
    private List<String> realNodes = new ArrayList<String>();
    // 模拟服务器列表
    private String[] servers =null;

    private void init(List<String> serviceList) {
        for (String server : serviceList) {
            realNodes.add(server);
            System.out.println("真实节点[" + server + "] 被添加");
            for (int i = 0; i < VIRTUAL_NUM; i++) {
                String virtualNode = server + "&&VM" + i;
                int hash = getHash(virtualNode);
                shards.put(hash, virtualNode);
                System.out.println("虚拟节点[" + virtualNode + "]被添加, hash值为" + hash);
            }
        }
    }
    private String getServer(String str, List<String> addressList) {
        // 先初始化哈希环
        init(addressList);
        int hash = getHash(str);
        SortedMap<Integer, String> subMap = shards.tailMap(hash);
        int key;
        if (subMap.isEmpty()) {
            key = shards.firstKey();
        } else {
            key = subMap.firstKey();
        }
        String virtualNode = subMap.get(key);
        return virtualNode.substring(0, virtualNode.indexOf("&&"));
    }

    /**
     * 添加节点
     *
     * @param node
     */
    public  void addNode(String node) {
        if (!realNodes.contains(node)) {
            realNodes.add(node);
            System.out.println("真实节点[" + node + "] 上线添加");
            for (int i = 0; i < VIRTUAL_NUM; i++) {
                String virtualNode = node + "&&VN" + i;
                int hash = getHash(virtualNode);
                shards.put(hash, virtualNode);
                System.out.println("虚拟节点[" + virtualNode + "] hash:" + hash + "，被添加");
            }
        }
    }

    /**
     * 删除节点
     *
     * @param node
     */
    public  void delNode(String node) {
        if (realNodes.contains(node)) {
            realNodes.remove(node);
            System.out.println("真实节点[" + node + "] 下线移除");
            for (int i = 0; i < VIRTUAL_NUM; i++) {
                String virtualNode = node + "&&VN" + i;
                int hash = getHash(virtualNode);
                shards.remove(hash);
                System.out.println("虚拟节点[" + virtualNode + "] hash:" + hash + "，被移除");
            }
        }
    }

    // 获取hash值
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }
    @Override
    public String balance(List<String> addressList) {
        String str = UUID.randomUUID().toString();
        return getServer(str, addressList);
    }
}
