package part1.common.service.impl;

import part1.common.pojo.User;
import part1.common.service.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    @Override
    public User getUserByUserId(Integer id) {
        Random random = new Random();
        String userName = UUID.randomUUID().toString();
        User user = User.builder()
                .id(id)
                .userName(userName)
                .sex(random.nextBoolean()).build();
        return user;
    }

    @Override
    public Integer insertUserId(User user) {
        System.out.println("插入数据成功" + user.getUserName());
        return user.getId();
    }
}
