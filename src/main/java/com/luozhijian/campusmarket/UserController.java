package com.luozhijian.campusmarket;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final JdbcTemplate jdbcTemplate;

    // 构造器注入：Spring 会自动把配好的 JdbcTemplate 塞进来
    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @GetMapping("/users/count")
    public Integer count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM `user`",Integer.class);
    }

    @GetMapping("/users")
    public List<Map<String, Object>> list() {
        return jdbcTemplate.queryForList("SELECT id, nickname, school_no, credit FROM `user`");
    }
}