package com.xiami.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Integer userId;

    private String content;

    private String userAvatar;

    //当前页
    private Integer currentPage;

    //每页大小
    private Integer pageSize;

    //被回复的用户id
    private Integer toUserId;

    //被回复的评论
    private Integer toCommentId;

    private String toUserAvatar;
}