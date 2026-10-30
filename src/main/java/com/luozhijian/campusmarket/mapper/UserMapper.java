package com.luozhijian.campusmarket.mapper;

import com.luozhijian.campusmarket.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户数据访问层：所有跟 user 表有关的 SQL 都写在这里
 */
@Repository
public class UserMapper {

    private final JdbcTemplate jdbcTemplate;

    public UserMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 结果集 → User 对象的映射规则
     * 这里是"下划线列名"翻译成"驼峰字段名"的地方
     */
    private static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setNickname(rs.getString("nickname"));
        u.setSchoolNo(rs.getString("school_no"));
        u.setCredit(rs.getInt("credit"));
        if (rs.getTimestamp("created_at") != null) {
            u.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        return u;
    };

    /**
     * 查询所有用户
     */
    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT id, nickname, school_no, credit, created_at FROM `user`",
                ROW_MAPPER);
    }

    /**
     * 统计用户总数
     */
    public int count() {
        Integer n = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM `user`", Integer.class);
        return n == null ? 0 : n;
    }

    /**
     * 新增用户，返回受影响行数
     */
    public int insert(String nickname, String schoolNo) {
        return jdbcTemplate.update(
                "INSERT INTO `user` (nickname, school_no) VALUES (?, ?)",
                nickname, schoolNo);
    }

    public int update(Long id, String nickname, Integer credit) {
        return jdbcTemplate.update(
                "UPDATE `user` SET nickname = ?, credit = ? WHERE id = ?", nickname, credit, id
        );
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update(
                "DELETE FROM `user` WHERE id = ?", id);
    }

    public User findById(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, nickname, school_no, credit, created_at FROM `user` WHERE id = ?",
                ROW_MAPPER,
                id);
    }

    public User findBySchoolNo(String schoolNo) {
        List<User> list = jdbcTemplate.query(
                "SELECT id, nickname, school_no, credit, created_at FROM `user` WHERE school_no = ?",
                ROW_MAPPER,
                schoolNo);
        return list.isEmpty() ? null : list.get(0);
    }

    public int insertUser(String nickname, String schoolNo, String password) {
        return jdbcTemplate.update(
                "INSERT INTO `user` (nickname, school_no, password) VALUES (?, ?, ?)",
                nickname, schoolNo, password);           // 3 个参数，干净
    }

    public String findPasswordBySchoolNo(String schoolNo) {
        List<String> list = jdbcTemplate.query(
                "SELECT password FROM `user` WHERE school_no = ?",
                (rs, rowNum) -> rs.getString("password"),
                schoolNo);
        return list.isEmpty() ? null : list.get(0);
    }
}