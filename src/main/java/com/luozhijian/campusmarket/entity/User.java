package com.luozhijian.campusmarket.entity;

import java.time.LocalDateTime;

/**
 * 用户实体类，对应数据库表 campus_market.user
 *
 * 为什么需要它？
 *   以前返回 List<Map<String,Object>>，字段名是"字符串"，写错了编译期发现不了。
 *   有了实体类：字段有类型、有名字，IDE 能补全、编译器能查错。
 */
public class User {

    private Long id;                  // 对应 id
    private String nickname;          // 对应 nickname
    private String schoolNo;          // 对应 school_no（下划线 → 驼峰）
    private Integer credit;           // 对应 credit
    private LocalDateTime createdAt;  // 对应 created_at

    public User() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getSchoolNo() { return schoolNo; }
    public void setSchoolNo(String schoolNo) { this.schoolNo = schoolNo; }

    public Integer getCredit() { return credit; }
    public void setCredit(Integer credit) { this.credit = credit; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}