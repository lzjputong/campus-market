package com.luozhijian.campusmarket.mapper;

import com.luozhijian.campusmarket.entity.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteMapper {                                          // ← 改这里

    private final JdbcTemplate jdbcTemplate;

    public FavoriteMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public int insert(Long userId, Long itemId) {      // ✅ 签名是对的
        return jdbcTemplate.update("INSERT INTO `favorite` (user_id, item_id) VALUES (?, ?)",
                userId, itemId);
    }
    public int delete(Long userId, Long itemId){
        return jdbcTemplate.update("DELETE FROM `favorite` WHERE user_id = ? AND item_id = ?",userId,itemId);
    }
    public List<Item> findItemsByUser(Long userId, int offset, int size) {
        return jdbcTemplate.query(
                "SELECT i.* FROM `favorite` f JOIN `item` i ON f.item_id = i.id "
                        + "WHERE f.user_id = ? ORDER BY f.created_at DESC LIMIT ? OFFSET ?",
                ItemMapper.ROW_MAPPER,
                userId,
                size,
                offset);
    }

    public int countByUser(Long userId) {
        Integer n = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM `favorite` WHERE user_id = ?",
                Integer.class,
                userId);
        return n == null ? 0 : n;
    }
}