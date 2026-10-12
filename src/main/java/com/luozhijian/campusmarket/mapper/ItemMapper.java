package com.luozhijian.campusmarket.mapper;

import com.luozhijian.campusmarket.entity.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
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
    /** 在售商品列表（分页）：categoryId 为 null 表示不筛分类 */
    public List<Item> findOnSale(Integer categoryId, int offset, int size) {
        // 第 1 步：固定前半段
        StringBuilder sql = new StringBuilder("SELECT * FROM `item` WHERE status = 0");
        // 第 2 步：空的参数列表
        List<Object> params = new ArrayList<>();

        // 第 3 步：有分类才拼进去
        if (categoryId != null) {
            sql.append(" AND category_id = ?");
            params.add(categoryId);
        }

        // 第 4 步：固定后半段
        sql.append(" ORDER BY created_at DESC LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        // 第 5 步：执行
        return jdbcTemplate.query(sql.toString(), ROW_MAPPER, params.toArray());
    }

    /** 在售商品总数 */
    public int countOnSale(Integer categoryId) {
        StringBuilder sql=new StringBuilder("SELECT COUNT(*) FROM `item` WHERE status = 0");
        List<Object> params = new ArrayList<>();
        if (categoryId != null){
            sql.append(" AND category_id = ?");
            params.add(categoryId);

        }
        return jdbcTemplate.queryForObject(sql.toString(), Integer.class, params.toArray());
    }
}