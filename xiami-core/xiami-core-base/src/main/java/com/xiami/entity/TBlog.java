package com.xiami.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "t_blog")
public class TBlog implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "appreciation")
    private Integer appreciation;

    @Column(name = "commentabled")
    private Integer commentabled;

    @Column(name = "content")
    private String content;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "description")
    private String description;

    @Column(name = "first_picture")
    private String firstPicture;

    @Column(name = "flag")
    private String flag;

    @Column(name = "published")
    private Integer published;

    @Column(name = "recommend")
    private Integer recommend;

    @Column(name = "share_statement")
    private Integer shareStatement;

    @Column(name = "title")
    private String title;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "views")
    private Integer views;

    @Column(name = "collect_count")
    private Integer collectCount;

    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "user_id")
    private Integer userId;

    private static final long serialVersionUID = 1L;

}