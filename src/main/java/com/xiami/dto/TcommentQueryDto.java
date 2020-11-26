package com.xiami.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­01 23:57
 */
@Data
public class TcommentQueryDto extends PageRequestDto {

    private String userName;
    private Set<Integer> userId;
    private String toUserName;
    private Set<Integer> toUserId;
    private String content;
    private Integer typeId;
    private String statusId;

    //起始时间
    private String beginTime;
    //结束时间
    private String endTime;
}
