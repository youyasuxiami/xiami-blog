package com.xiami.dto;

import lombok.Data;

import java.util.Date;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­01 0:08
 */
@Data
public class BlogListDto {
    private Integer id;

    private String content;
    private Date createTime;
    private String description;
    private String firstPicture;

    private String flag;

    private String publish;

    private String title;

    private Date updateTime;

    private Integer views;

    private String typeName;

    private String userName;

    private String recommend;

    private Integer shareStatement;

    private Integer appreciation;

    private Integer commentabled;

    private Integer typeId;

    private Integer tagId;
}
