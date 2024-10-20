package com.example.yin.service.impl;

import com.example.yin.model.domain.Comment;
import com.example.yin.dao.CommentMapper;
import com.example.yin.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2024-10-18
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
