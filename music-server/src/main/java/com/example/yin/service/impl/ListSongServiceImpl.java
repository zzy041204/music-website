package com.example.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yin.common.R;
import com.example.yin.model.domain.ListSong;
import com.example.yin.dao.ListSongMapper;
import com.example.yin.service.ListSongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yin.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2024-10-18
 */
@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements ListSongService {

    @Autowired
    SongService songService;

    @Override
    public R getListSongOfSongId(Integer songListId) {
        List<ListSong> listSongs = this.list(new QueryWrapper<ListSong>().eq("song_list_id", songListId));
        if (listSongs != null && listSongs.size() > 0) {
            return R.success("成功返回指定歌单的歌曲",listSongs);
        }else {
            return R.error("返回指定歌单歌曲失败");
        }
    }

//    @Override
//    public R getListSongOfSongId(Integer songListId) {
//        List<ListSong> listSongs = this.list(new QueryWrapper<ListSong>().eq("song_list_id", songListId));
//        List<Integer> songIds = listSongs.stream().map(l -> l.getSongId()).collect(Collectors.toList());
//        List<Song> songs = songService.list(new QueryWrapper<Song>().in("id", songIds));
//        if (songs != null && songs.size() > 0) {
//            return R.success("成功返回指定歌单的歌曲",songs);
//        }else{
//            return R.error("返回指定歌单歌曲失败");
//        }
//    }


}
