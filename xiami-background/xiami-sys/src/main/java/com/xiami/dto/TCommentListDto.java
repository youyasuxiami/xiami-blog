package com.xiami.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class TCommentListDto {
    private Integer id;

    /**
     * 一级评论的id
     */
    private Integer firstCommentId;

    /**
     * 上一级的评论id
     */
    private Integer parentCommentId;

    /**
     * 回复者的用户id
     */
    private Integer userId;

    /**
     * 回复者的用户名
     */
    private String userName;

    /**
     * 被回复者的用户id
     */
    private Integer toUserId;

    /**
     * 被回复者的用户名
     */
    private String toUserName;

    /**
     * 回复者的头像
     */
    private String userAvatar;

    /**
     * 被回复者的头像
     */
    private String toUserAvatar;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 博客id
     */
    private Integer blogId;

    /**
     * 点赞数
     */
    private Integer likes;

    /**
     * 审核状态 1：待审核 2：通过审核 3：审核不通过
     */
    private String status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 评论类型： 0评论 1点赞
     */
    private String type;
}
