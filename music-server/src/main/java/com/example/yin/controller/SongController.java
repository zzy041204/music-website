package com.example.yin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yin.common.R;
import com.example.yin.domain.Song;
import com.example.yin.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/song")
public class SongController {

    @Autowired
    SongService songService;

    /**
     * 返回所有歌曲
     * @return
     */
    @GetMapping("")
    public R getAllSong(){
        List<Song> list = songService.list();
        return R.success("所有歌曲返回成功",list);
    }


    /**
     * 返回指定歌手的所有歌曲
     * @param singerId
     * @return
     */
    @GetMapping("/singer/detail")
    public R getSongOfSingerId(@RequestParam("singerId") Integer singerId){
        List<Song> songs = songService.list(new QueryWrapper<Song>().eq("singer_id", singerId));
        if(songs != null && songs.size() > 0){
            return R.success("返回指定歌手ID的歌曲成功",songs);
        }else {
            return R.error("返回指定歌手ID的歌曲失败");
        }
    }

    /**
     * 返回指定用户的收藏列表
     * @return
     */
    @GetMapping("detail")
    public R getSongOfId(@RequestParam("id") Integer id){
        return songService.getCollectSongsByUserId(id);
    }

    /**
     * 返回指定歌手名的所有歌曲
     */
    @GetMapping("/singerName/detail")
    public R getSongOfSingerName(@RequestParam("name") String name){
        return songService.getSongOfSingerName(name);
    }

    /**
     * 更新歌曲信息
     * @param song
     * @return
     */
    @PostMapping("/update")
    public R updateSongMsg(@RequestBody Song song){
        boolean update = songService.updateById(song);
        if(update){
            return R.success("更新歌曲信息成功");
        }else {
            return R.error("更新歌曲失败");
        }
    }

    /**
     * 更新歌曲图片
     * @param urlFile
     * @param id
     * @return
     */
    @PostMapping("/img/update")
    public R updateSongPic(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") int id) {
        return songService.updateSongPic(urlFile,id);
    }

    /**
     * 更新歌曲
     * @param urlFile
     * @param id
     * @return
     */
    @PostMapping("/url/update")
    public R updateSongUrl(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") int id) {
        return songService.updateSongUrl(urlFile,id);
    }

    /**
     * 更新歌词
     * @param lrcFile
     * @param id
     * @return
     */
    @PostMapping("/lrc/update")
    public R updateSongLrc(@RequestParam("file") MultipartFile lrcFile, @RequestParam("id") int id) {
        return songService.updateSongLrc(lrcFile, id);
    }

    /**
     * 删除歌曲
     */
    @DeleteMapping("/delete")
    public R deleteSong(@RequestParam("id") Integer id){
        return songService.deleteSong(id);
    }



}
