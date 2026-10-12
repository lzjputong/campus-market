package com.luozhijian.campusmarket.service;

import com.luozhijian.campusmarket.entity.Item;
import com.luozhijian.campusmarket.mapper.ItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemMapper itemMapper;

    public ItemService(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    /** 发布商品，返回受影响行数 */
    public int publish(Item item) {
        return itemMapper.insert(item);
    }

    /** 按 id 查商品 */
    public Item getById(Long id) {
        return itemMapper.findById(id);
    }

    /** 在售商品列表（分页）：收"页码"，换算成"跳过几条" */
    public List<Item> listOnSale(int page, int size) {
        int offset = (page - 1) * size;
        return itemMapper.findOnSale(offset, size);
    }

    /** 在售商品总数 */
    public int countOnSale() {
        return itemMapper.countOnSale();
    }
}