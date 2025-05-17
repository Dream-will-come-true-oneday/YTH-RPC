package rpc.Client.serviceCenter.balance;

import java.util.List;

public interface LoadBalance {
    public String balance(List<String> addressList);
    public void addNode(String node);
    public void delNode(String node);
}
