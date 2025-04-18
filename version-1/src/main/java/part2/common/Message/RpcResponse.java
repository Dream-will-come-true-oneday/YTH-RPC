package part2.common.Message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RpcResponse {
    // 状态信息
    private Integer code;
    private String message;
    // 具体数据
    private Object data;

    // 构造成功信息
    public static RpcResponse success(Object data) {
        return RpcResponse.builder().code(200).data(data).build();
    }
    // 构造失败信息
    public static RpcResponse fail() {
        return RpcResponse.builder().code(500).message("服务端出现错误").build();
    }
}
