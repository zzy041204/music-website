package com.example.yin.service;

import com.example.yin.common.R;
import com.example.yin.model.domain.Collect;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2024-10-18
 */
public interface CollectService extends IService<Collect> {

    R getCollectionOfUser(Integer userId);

    R deleteCollection(Integer userId, Integer songId);
}
