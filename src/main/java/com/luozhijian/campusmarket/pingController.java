package com.luozhijian.campusmarket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class pingController {
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam String name) {
        return "你好，" + name;
    }

}
