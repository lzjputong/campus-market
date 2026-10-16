package com.luozhijian.campusmarket.controller;

import com.luozhijian.campusmarket.common.Result;
import com.luozhijian.campusmarket.entity.Favorite;
import com.luozhijian.campusmarket.entity.Item;
import com.luozhijian.campusmarket.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FavoriteController {
    private final FavoriteService favoriteService;
    public FavoriteController(FavoriteService favoriteService){
        this.favoriteService=favoriteService;
    }
    @PostMapping("/favorites/{itemId}")
    public Result<Integer> add(@PathVariable Long itemId ,@RequestParam Long userId){
        return Result.ok(favoriteService.add(userId, itemId));
    }
    @DeleteMapping("/favorites/{itemId}")
    public Result<Integer> cancel(@PathVariable Long itemId,@RequestParam Long userId){
        return Result.ok(favoriteService.remove(userId,itemId));
    }
    @GetMapping("/favorites")
    public Result<List<Item>> listItems(@RequestParam Long userId, @RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="10") int size){
        return Result.ok(favoriteService.listItems(userId,page,size));
    }
    @GetMapping("/favorites/count")
    public Result<Integer> count(@RequestParam Long userId){
        return Result.ok(favoriteService.count(userId));
    }
}
