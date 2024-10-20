package com.example.yin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yin.common.R;
import com.example.yin.model.domain.ListSong;
import com.example.yin.service.ListSongService;
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
@RequestMapping("/listSong")
public class ListSongController {

    @Autowired
    ListSongService listSongService;

    /**
     * 歌单中添加歌曲
     * @param listSong
     * @return
     */
    @PostMapping("/add")
    public R setListSong(@RequestBody ListSong listSong){
        boolean save = listSongService.save(listSong);
        if(save){
            return R.success("歌单添加歌曲成功");
        }else {
            return R.error("歌单添加歌曲失败");
        }
    }

    /**
     * 返回歌单里指定歌单ID的歌曲
     * @param songListId
     * @return
     */
    @GetMapping("/detail")
    public R getListSongOfSongId(@RequestParam("songListId") Integer songListId){
        return listSongService.getListSongOfSongId(songListId);
    }

    @GetMapping("/delete")
    public R deleteListSong(@RequestParam("songId") Integer songId){
        boolean remove = listSongService.remove(new QueryWrapper<ListSong>().eq("song_id", songId));
        if (remove){
            return R.success("删除成功");
        }else {
            return R.error("删除失败");
        }
    }

}
