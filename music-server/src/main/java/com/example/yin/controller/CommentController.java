package com.example.yin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yin.common.R;
import com.example.yin.model.domain.Comment;
import com.example.yin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 获取指定歌曲的评论列表
     * @param songId
     * @return
     */
    @GetMapping("/song/detail")
    public R getCommentOfSongId(@RequestParam("songId") Integer songId){
        List<Comment> commentList = commentService.list(new QueryWrapper<Comment>().eq("song_id", songId));
        if(commentList != null && commentList.size() > 0){
            return R.success("获得指定歌曲的评论列表成功",commentList);
        }else {
            return R.warning("该歌曲暂无评论");
        }
    }

    /**
     * 获取指定歌单的评论列表
     * @param songListId
     * @return
     */
    @GetMapping("/songList/detail")
    public R getCommentOfSongListId(@RequestParam("songListId") Integer songListId){
        List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("song_list_id", songListId));
        if(comments != null && comments.size() > 0){
            return R.success("获得指定歌单的评论列表成功",comments);
        }else {
            return R.warning("该歌单暂无评论");
        }
    }

    /**
     * 删除指定评论
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public R deleteComment(@RequestParam("id") Integer id){
        boolean remove = commentService.removeById(id);
        if (remove) {
            return R.success("删除指定评论成功");
        }else {
            return R.error("删除指定评论失败");
        }
    }


}
