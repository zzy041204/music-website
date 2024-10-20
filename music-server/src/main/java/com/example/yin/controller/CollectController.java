package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzy
 * @since 2024-10-18
 */
@RestController
@RequestMapping("/collection")
public class CollectController {

    @Autowired
    CollectService collectService;

    /**
     * 返回指定用户的收藏列表
     * @param userId
     * @return
     */
    @GetMapping("/detail")
    public R getCollectionOfUser(@RequestParam("userId") Integer userId){
        return collectService.getCollectionOfUser(userId);
    }

    /**
     * 删除收藏歌曲
     */
    @DeleteMapping("/delete")
    public R deleteCollection(@RequestParam("userId") Integer userId,@RequestParam("songId") Integer songId){
        return collectService.deleteCollection(userId,songId);
    }

}
