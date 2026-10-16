package com.luozhijian.campusmarket.service;

import com.luozhijian.campusmarket.entity.Item;
import com.luozhijian.campusmarket.exception.BusinessException;
import com.luozhijian.campusmarket.mapper.FavoriteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteMapper favoriteMapper;      // ✅ 声明时【不给值】

    public FavoriteService(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    public int add(Long userId, Long itemId){
        return favoriteMapper.insert(userId, itemId);
    }
    public int remove(Long userId, Long itemId){
        int rows=favoriteMapper.delete(userId,itemId);
        if (rows == 0) {
            throw new BusinessException(4004, "还没有收藏这商品");
        }
        return rows;
    }
    public List<Item> listItems(Long userId, int page, int size){
        int offset = (page - 1) * size;
        return favoriteMapper.findItemsByUser(userId, offset, size);
    }
    public int count(Long userId) {
        return favoriteMapper.countByUser(userId);
    }
}
