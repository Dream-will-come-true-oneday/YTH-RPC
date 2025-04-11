package part1.common.service;

import part1.common.pojo.User;

public interface UserService {
    // 根据id查询用户
    User getUserByUserId(Integer id);
    // 新增一个功能
    Integer insertUserId(User user);
}
