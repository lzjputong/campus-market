package com.luozhijian.campusmarket.controller;

import com.luozhijian.campusmarket.common.Result;
import com.luozhijian.campusmarket.entity.Item;
import com.luozhijian.campusmarket.service.ItemService;
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
        return Result.ok(itemService.listOnSale(categoryId, page, size));
    }

    @GetMapping("/items/count")
    public Result<Integer> count(@RequestParam(required = false) Integer categoryId) {
        return Result.ok(itemService.countOnSale(categoryId));
    }
    @PutMapping("/items/{id}/status")
    public Result<Integer> updateStatus(@PathVariable Long id, @RequestParam Integer status){
        return Result.ok(itemService.updateStatus(id, status));
    }
    @GetMapping("/items/mine")
    public Result<List<Item>> listBySeller(@RequestParam Long sellerId, @RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="10") int size){
        return Result.ok(itemService.listBySeller(sellerId, page, size));
    }
    @GetMapping("/items/mine/count")
    public Result<Integer> countBySeller(@RequestParam Long sellerId){
        return Result.ok(itemService.countBySeller(sellerId));
    }
}