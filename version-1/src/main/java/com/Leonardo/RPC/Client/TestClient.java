package com.Leonardo.RPC.Client;

import com.Leonardo.RPC.Client.proxy.ClientProxy;
import com.Leonardo.RPC.common.pojo.User;
import com.Leonardo.RPC.common.service.UserService;

public class TestClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8888);
        UserService proxy = clientProxy.getProxy(UserService.class);

        User user = proxy.getUserByUserId(1);
        System.out.println("从服务端得到的user="+user.toString());

        User u = User.builder().id(100).userName("wxx").sex(true).build();
        Integer id = proxy.insertUserId(u);
        System.out.println("插入结果："+id);
    }
}
