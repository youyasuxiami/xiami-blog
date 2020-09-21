package com.xiami.dto;

import lombok.Data;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­09­06 21:55
 */
@Data
public class CommentDto {
    private Integer userId;

    private String content;

    private Integer blogId;

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
