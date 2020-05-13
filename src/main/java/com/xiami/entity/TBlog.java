package com.xiami.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "t_blog")
public class TBlog implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "appreciation")
    private Boolean appreciation;

    @Column(name = "commentabled")
    private Boolean commentabled;

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
    private Boolean published;

    @Column(name = "recommend")
    private Boolean recommend;

    @Column(name = "share_statement")
    private Boolean shareStatement;

    @Column(name = "title")
    private String title;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "views")
    private Integer views;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "user_id")
    private Long userId;

    private static final long serialVersionUID = 1L;
}