package com.luozhijian.campusmarket.controller;

import com.luozhijian.campusmarket.common.Result;
import com.luozhijian.campusmarket.dto.LoginRequest;
import com.luozhijian.campusmarket.dto.LoginResponse;
import com.luozhijian.campusmarket.dto.RegisterRequest;
import com.luozhijian.campusmarket.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/register")
    public Result<Integer> register(@RequestBody RegisterRequest req) {
        return Result.ok(authService.register(req));
    }
    @PostMapping("/auth/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest req) {
        return Result.ok(authService.login(req));
    }
}