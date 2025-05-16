package com.Leonardo.RPC.Client.serviceCenter.ZKWatcher;

import com.Leonardo.RPC.Client.cache.ServiceCache;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;

public class watchZK {
    private CuratorFramework client;
    private ServiceCache cache;

    public watchZK(CuratorFramework client, ServiceCache cache) {
        this.client = client;
        this.cache = cache;
    }

    public void watchToUpdate(String path) throws InterruptedException{
        CuratorCache curatorCache = CuratorCache.build(client, "/" + path);
        curatorCache.listenable().addListener(new CuratorCacheListener() {
            @Override
            public void event(Type type, ChildData childData, ChildData childData1) {
                switch (type.name()) {
                    case "NODE_CREATED":
                        String[] pathList = parseAddress(childData1);
                        if (pathList.length <= 2)
                            break;
                        String serviceName = pathList[1];
                        String address = pathList[2];
                        cache.addServiceToCache(serviceName, address);
                        break;
                    case "NODE_CHANGED":
                        if (childData.getPath() != null) {
                            System.out.println("修改前的数据：" + new String(childData.getData()));
                        } else {
                            System.out.println("节点第一次赋值");
                        }
                        String[] oldPathList = parseAddress(childData);
                        String[] newPathList = parseAddress(childData1);
                        cache.replaceServiceAddress(oldPathList[1], oldPathList[2], newPathList[2]);
                        System.out.println("修改后的数据：" + new String(childData1.getData()));
                        break;
                    case "NODE_DELETED":
                        String[] pathList_d = parseAddress(childData);
                        if (pathList_d.length <= 2) break;
                        cache.removeService(pathList_d[1], pathList_d[2]);
                        break;
                }
            }
        });
        curatorCache.start();
    }

    public String[] parseAddress(ChildData childData) {
        String path = new String(childData.getData());
        return path.split("/");
    }
}
