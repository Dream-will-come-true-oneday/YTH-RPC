package part2.Client.rpcClient;

import part2.common.Message.RpcRequest;
import part2.common.Message.RpcResponse;

public interface RpcClient {
    // 共性抽取出来，定义底层通信的方法
    RpcResponse sendRequest(RpcRequest request);
}
