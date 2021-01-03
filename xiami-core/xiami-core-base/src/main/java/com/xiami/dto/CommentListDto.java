package com.xiami.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­09­06 21:55
 */
@Data
public class CommentListDto {
    private Integer id;
    private Integer firstCommentId;
    private Integer parentCommentId;
    private Integer userId;
    private Integer toUserId;
    private String userAvatar;
    private String toUserAvatar;
    private String content;
    private Integer blogId;
    private Integer likes;
    /**
     * 审核状态 1：待审核 2：通过审核 3：审核不通过
     */
    private String status;
    private Date createTime;
    private Date updateTime;
    private String type;

    //用户姓名
    private String userName;

    //被@也就是被回复的用户姓名
    private String toUserName;

    private List<CommentListDto> replyList;
}
