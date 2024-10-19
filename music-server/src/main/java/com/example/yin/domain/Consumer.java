package com.example.yin.domain;

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
@TableName("consumer")
public class Consumer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("sex")
    private Byte sex;

    @TableField("phone_num")
    private String phoneNum;

    @TableField(value = "email")
    private String email;

    @TableField("birth")
    private LocalDateTime birth;

    @TableField("introduction")
    private String introduction;

    @TableField("location")
    private String location;

    @TableField("avator")
    private String avator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
