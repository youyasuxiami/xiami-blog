package com.xiami.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "t_comment")
public class TComment implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 一级评论的id
     */
    @Column(name = "first_comment_id")
    private Integer firstCommentId;

    /**
     * 上一级的评论id
     */
    @Column(name = "parent_comment_id")
    private Integer parentCommentId;

    /**
     * 回复者的用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 被回复者的用户id
     */
    @Column(name = "to_user_id")
    private Integer toUserId;

    /**
     * 回复者的头像
     */
    @Column(name = "user_avatar")
    private String userAvatar;

    /**
     * 被回复者的头像
     */
    @Column(name = "to_user_avatar")
    private String toUserAvatar;

    /**
     * 评论内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 博客id
     */
    @Column(name = "blog_id")
    private Integer blogId;

    /**
     * 点赞数
     */
    @Column(name = "likes")
    private Integer likes;

    /**
     * 审核状态 1：待审核 2：通过审核 3：审核不通过
     */
    @Column(name = "`status`")
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 评论类型： 0评论 1点赞
     */
    @Column(name = "`type`")
    private String type;

    /**
     * 本条评论下的回复
     */
    private List<TComment> replyList;

    private static final long serialVersionUID = 1L;
}