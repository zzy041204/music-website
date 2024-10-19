package com.example.yin.service;

import com.example.yin.common.R;
import com.example.yin.domain.Song;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2024-10-18
 */
public interface SongService extends IService<Song> {

    List<Song> getCollectSongsByUserId(Integer id);

    List<Song> getSongOfSingerName(String name);

    R updateSongPic(MultipartFile urlFile, int id);

    R updateSongUrl(MultipartFile urlFile, int id);

    R updateSongLrc(MultipartFile lrcFile, int id);

    R deleteSong(Integer id);
}
