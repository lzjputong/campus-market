package com.luozhijian.campusmarket.service;

import com.luozhijian.campusmarket.entity.Item;
import com.luozhijian.campusmarket.exception.BusinessException;
import com.luozhijian.campusmarket.mapper.ItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemMapper itemMapper;

    public ItemService(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    /**
     * 发布商品，返回受影响行数
     */
    public int publish(Item item) {
        return itemMapper.insert(item);
    }

    /**
     * 按 id 查商品
     */
    public Item getById(Long id) {
        return itemMapper.findById(id);
    }

    /**
     * 在售商品列表（分页）：收"页码"，换算成"跳过几条"
     */
    public List<Item> listOnSale(Integer categoryId, int page, int size) {
        int offset = (page - 1) * size;
        return itemMapper.findOnSale(categoryId, offset, size);
    }

    public int updateStatus(Long id, Integer status) {
        if (status == null || status < 0 || status > 3) {
            throw new BusinessException(4002, "状态值不合法");
        }
        int rows = itemMapper.updateStatus(id, status);
        if (rows == 0) {
            throw new BusinessException(4004, "商品不存在");
        }
        return rows;
    }

    public List<Item> listBySeller(Long sellerId, int page, int size) {
        int offset = (page - 1) * size;
        return itemMapper.findBySeller(sellerId, offset, size);
    }

    public int countBySeller(Long sellerId) {
        return itemMapper.countBySeller(sellerId);
    }

    /**
     * 在售商品总数
     */
    public int countOnSale(Integer categoryId) {
        return itemMapper.countOnSale(categoryId);
    }
}