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
public class CommentDeteleDto {
     //被删除的评论id
     private Integer commentId;
     //当前登录账号的id
     private Integer userId;
}
