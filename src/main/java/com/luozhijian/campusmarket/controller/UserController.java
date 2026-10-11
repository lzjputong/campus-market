package com.luozhijian.campusmarket.controller;

import com.luozhijian.campusmarket.common.Result;
import com.luozhijian.campusmarket.entity.User;
import com.luozhijian.campusmarket.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Result<List<User>> list() {
        return Result.ok(userService.listAll());
    }

    @GetMapping("/users/count")
    public Result<Integer> count() {
        return Result.ok(userService.count());
    }
    @GetMapping("/users/{id}")
    public Result<User> findById(@PathVariable Long id) {
        return  Result.ok(userService.findById(id));
    }

    @PostMapping("/users")
    public Result<Integer> add(@RequestParam String nickname, @RequestParam String schoolNo) {
        return Result.ok(userService.create(nickname, schoolNo));
    }
    @DeleteMapping("/users/{id}")
    public Result<Integer> delete( @PathVariable Long id) {
        return Result.ok(userService.delete(id));
    }
    @PutMapping("/users/{id}")
    public Result<Integer>update(@PathVariable long id,@RequestParam String nickname,@RequestParam Integer credit){
        return Result.ok(userService.update(id, nickname, credit));
    }
}