package com.luozhijian.campusmarket.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.luozhijian.campusmarket.dto.LoginRequest;
import com.luozhijian.campusmarket.dto.LoginResponse;
import com.luozhijian.campusmarket.dto.RegisterRequest;
import com.luozhijian.campusmarket.entity.User;
import com.luozhijian.campusmarket.exception.BusinessException;
import com.luozhijian.campusmarket.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private final UserMapper userMapper;
    @Value("${jwt.secret}")
    private String secret;
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
    /** 登录：校验密码，生成 token */
    public LoginResponse login(LoginRequest req) {

        // ① 按学号查密码密文
        String stored = userMapper.findPasswordBySchoolNo(req.getSchoolNo());

        // ② 学号不存在 → 统一提示"学号或密码错误"（防账号枚举）
        if (stored == null) {
            throw new BusinessException(4003, "学号或密码错误");
        }

        // ③ 校验密码（checkpw 返回 true 表示密码正确，所以要取反）
        if (!BCrypt.checkpw(req.getPassword(), stored)) {
            throw new BusinessException(4003, "学号或密码错误");
        }

        // ④ 查完整用户（学号已在上一步证实存在，不会为 null）
        User user = userMapper.findBySchoolNo(req.getSchoolNo());

        // ⑤ 生成 token：payload 里只放 userId
        String token = JWT.create()
                .withClaim("userId", user.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + 7L * 24 * 3600 * 1000))
                .sign(Algorithm.HMAC256(secret));

        // ⑥ 组装返回
        LoginResponse resp = new LoginResponse();
        resp.setToken(token);
        resp.setUser(user);
        return resp;
    }
}

