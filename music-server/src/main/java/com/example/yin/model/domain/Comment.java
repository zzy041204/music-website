package com.example.yin.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author zzy
 * @since 2024-10-18
 */
@Getter
@Setter
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("song_id")
    private Integer songId;

    @TableField("song_list_id")
    private Integer songListId;

    @TableField("content")
    private String content;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField("type")
    private Byte type;

    @TableField("up")
    private Integer up;
}
