package com.luozhijian.campusmarket.mapper;

import com.luozhijian.campusmarket.entity.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemMapper {

    private final JdbcTemplate jdbcTemplate;

    public ItemMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** 结果集 → Item 对象的映射规则 */
    private static final RowMapper<Item> ROW_MAPPER = (rs, rowNum) -> {
        Item item = new Item();
        item.setId(rs.getLong("id"));
        item.setSellerId(rs.getLong("seller_id"));
        item.setTitle(rs.getString("title"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getBigDecimal("price"));
        item.setCategoryId(rs.getInt("category_id"));
        item.setTradePlace(rs.getString("trade_place"));
        item.setConditionLevel(rs.getInt("condition_level"));
        item.setStatus(rs.getInt("status"));
        item.setViewCount(rs.getInt("view_count"));
        if (rs.getTimestamp("created_at") != null) {
            item.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            item.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        return item;
    };

    /** 发布商品，返回受影响行数 */
    public int insert(Item item) {
        return jdbcTemplate.update(
                "INSERT INTO `item` (seller_id, title, description, price, category_id, trade_place, condition_level) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                item.getSellerId(),
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getCategoryId(),
                item.getTradePlace(),
                item.getConditionLevel());
    }

    /** 按 id 查商品 */
    public Item findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM `item` WHERE id = ?",
                ROW_MAPPER,
                id);
    }

    /** 在售商品列表（分页） */
    public List<Item> findOnSale(int offset, int size) {
        return jdbcTemplate.query(
                "SELECT * FROM `item` WHERE status = 0 ORDER BY created_at DESC LIMIT ? OFFSET ?",
                ROW_MAPPER,
                size,
                offset);
    }

    /** 在售商品总数 */
    public int countOnSale() {
        Integer n = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM `item` WHERE status = 0", Integer.class);
        return n == null ? 0 : n;
    }
}