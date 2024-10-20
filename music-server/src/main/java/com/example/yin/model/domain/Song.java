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
@TableName("song")
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("singer_id")
    private Integer singerId;

    @TableField("name")
    private String name;

    @TableField("introduction")
    private String introduction;

    /**
     * 发行时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField("pic")
    private String pic;

    @TableField("lyric")
    private String lyric;

    @TableField("url")
    private String url;
}
