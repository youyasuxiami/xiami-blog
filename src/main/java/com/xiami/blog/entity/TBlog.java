package com.xiami.blog.entity;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "myblog..t_blog")
@Data
public class TBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean appreciation;

    private Boolean commentabled;

    @Column(name = "create_time")
    private Date createTime;

    private String description;

    @Column(name = "first_picture")
    private String firstPicture;

    private String flag;

    private Boolean published;

    private Boolean recommend;

    @Column(name = "share_statement")
    private Boolean shareStatement;

    private String title;

    @Column(name = "update_time")
    private Date updateTime;

    private Integer views;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "user_id")
    private Long userId;

    private String content;
}