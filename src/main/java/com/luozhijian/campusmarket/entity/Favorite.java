package com.luozhijian.campusmarket.entity;
import java.time.LocalDateTime;
public class Favorite {

    private Long userId;
    private Long itemId;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private LocalDateTime createdAt;

    public Favorite() {
    }
}