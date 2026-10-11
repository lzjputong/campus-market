package com.luozhijian.campusmarket.service;

import com.luozhijian.campusmarket.entity.User;
import com.luozhijian.campusmarket.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户业务层
 *
 * 目前这几个方法只是"转发"给 Mapper，看起来多余。
 * 但等有了真正的业务规则（比如"只能下架自己的商品"），
 * 它才是唯一该放业务逻辑的地方。
 */
@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> listAll() {
        return userMapper.findAll();
    }
    public User findById(long id) {
        return userMapper.findById(id);
    }

    public int count() {
        return userMapper.count();
    }

    public int create(String nickname, String schoolNo) {
        return userMapper.insert(nickname, schoolNo);
    }
}