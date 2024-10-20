package com.example.yin.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("rank_list")
public class RankList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("song_list_id")
    private Long songListId;

    @TableField("consumer_id")
    private Long consumerId;

    @TableField("score")
    private Integer score;
}
