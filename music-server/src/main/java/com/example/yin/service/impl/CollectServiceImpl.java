package com.example.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yin.common.R;
import com.example.yin.model.domain.Collect;
import com.example.yin.dao.CollectMapper;
import com.example.yin.service.CollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Override
    public R getCollectionOfUser(Integer userId) {
        List<Collect> collects = this.list(new QueryWrapper<Collect>().eq("user_id", userId));
        if (collects != null && collects.size() > 0) {
            return R.success("用户收藏列表返回成功",collects);
        }else {
            return R.error("用户收藏列表返回失败");
        }
    }

    @Override
    public R deleteCollection(Integer userId, Integer songId) {
        boolean remove = this.remove(new QueryWrapper<Collect>().eq("user_id", userId).eq("song_id", songId));
        if (remove){
            return R.success("删除成功");
        }else{
            return R.error("删除失败");
        }
    }
}
