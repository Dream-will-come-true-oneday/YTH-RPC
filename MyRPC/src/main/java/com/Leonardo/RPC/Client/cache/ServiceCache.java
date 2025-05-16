package com.Leonardo.RPC.Client.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceCache {

    Map<String, List<String>> cache = new HashMap<>();

    // 增加服务
    public void addServiceToCache(String serviceName, String address) {
        // 实现添加服务的逻辑
        if (cache.containsKey(serviceName)) {
            cache.get(serviceName).add(address);
            System.out.println("将name为" + serviceName + "和地址为" + address + "的服务添加到本地");
        } else {
            List<String> addresses = new ArrayList<>();
            addresses.add(address);
            cache.put(serviceName, addresses);
            System.out.println("新增" + serviceName + "服务，地址为" + address);
        }
    }

    // 修改服务
    public void replaceServiceAddress(String serviceName, String oldAddress, String newAddress) {
        if(cache.containsKey(serviceName)){
            List<String> addressList=cache.get(serviceName);
            addressList.remove(oldAddress);
            addressList.add(newAddress);
        } else {
            System.out.println("修改失败，服务不存在");
        }
    }

    // 从缓存中查找服务
    public List<String> getServiceFromCache(String serviceName) {
        return cache.get(serviceName);
    }

    // 移除服务
    public void removeService(String serviceName, String address) {
        if(cache.containsKey(serviceName)){
            cache.get(serviceName).remove(address);
            System.out.println("将name为" + serviceName + "和地址为" + address + "的服务从本地移除");
        }
    }
}
