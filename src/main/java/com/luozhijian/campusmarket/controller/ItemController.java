package com.luozhijian.campusmarket.controller;

import com.luozhijian.campusmarket.common.Result;
import com.luozhijian.campusmarket.entity.Item;
import com.luozhijian.campusmarket.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/items")
    public Result<Integer> publish(@RequestBody Item item) {
        return Result.ok(itemService.publish(item));
    }

    @GetMapping("/items/{id}")
    public Result<Item> detail(@PathVariable Long id) {
        return Result.ok(itemService.getById(id));
    }

    @GetMapping("/items")
    public Result<List<Item>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,@RequestParam(required = false) Integer categoryId) {
        return Result.ok(itemService.listOnSale(page, size,categoryId));
    }

    @GetMapping("/items/count")
    public Result<Integer> count(@RequestParam(required = false) Integer categoryId) {
        return Result.ok(itemService.countOnSale(categoryId));
    }
}