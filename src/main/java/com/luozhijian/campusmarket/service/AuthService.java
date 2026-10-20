package com.luozhijian.campusmarket.service;

import com.luozhijian.campusmarket.dto.RegisterRequest;
import com.luozhijian.campusmarket.exception.BusinessException;
import com.luozhijian.campusmarket.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;

    public AuthService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /** 注册：返回受影响行数 */
    public int register(RegisterRequest req) {
        // ① 检查学号是否已注册（findBySchoolNo 查不到返回 null）
        if (userMapper.findBySchoolNo(req.getSchoolNo()) != null) {
            throw new BusinessException(4001, "该学号已被注册");
        }

        // ② 密码加密
        String hashed = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());

        // ③ 插入（第三个参数是 hashed，不是明文密码）
        return userMapper.insertUser(req.getNickname(), req.getSchoolNo(), hashed);
    }
}

