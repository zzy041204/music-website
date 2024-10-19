package com.example.yin.service;

import com.example.yin.common.R;
import com.example.yin.domain.ListSong;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2024-10-18
 */
public interface ListSongService extends IService<ListSong> {

    R getListSongOfSongId(Integer songListId);

}
