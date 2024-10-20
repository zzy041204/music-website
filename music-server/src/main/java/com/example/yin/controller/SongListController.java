package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.model.domain.SongList;
import com.example.yin.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzy
 * @since 2024-10-18
 */
@RestController
@RequestMapping("/songList")
public class SongListController {

    @Autowired
    SongListService songListService;

    @GetMapping("")
    public R getSongList(){
        List<SongList> list = songListService.list();
        if ( list != null && list.size() > 0 ) {
            return R.success("成功获取全部歌单",list);
        }else {
            return R.error("获取歌单失败");
        }
    }

    /**
     * 新增歌单
     * @param songList
     * @return
     */
    @PostMapping("/add")
    public R setSongList(@RequestBody SongList songList){
        boolean save = songListService.save(songList);
        if (save){
            return R.success("新增歌单成功");
        }else {
            return R.error("新增歌单失败");
        }
    }

    /**
     * 更新歌单
     * @param songList
     * @return
     */
    @PostMapping("/update")
    public R updateSongListMsg(@RequestBody SongList songList){
        boolean update = songListService.updateById(songList);
        if (update){
            return R.success("歌单更新成功");
        }else {
            return R.error("歌单更新失败");
        }
    }

    /**
     * 删除歌单
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public R deleteSongList(@RequestParam("id") Integer id){
        boolean delete = songListService.removeById(id);
        if (delete){
            return R.success("歌单删除成功");
        }else {
            return R.error("歌单删除失败");
        }
    }

}
