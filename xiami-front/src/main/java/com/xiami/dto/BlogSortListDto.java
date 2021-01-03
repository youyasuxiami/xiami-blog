package com.xiami.dto;

import com.xiami.entity.TTag;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BlogSortListDto {
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

    private Integer userId;

    private String recommend;

    private Integer shareStatement;

    private Integer appreciation;

    private Integer commentabled;

    private Integer typeId;

    private List<TTag> tagList;
}