package com.xiami.dto;

import lombok.Data;

import java.util.Set;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­01 23:57
 */
@Data
public class TcommentReportQueryDto extends PageRequestDto {

    private String reportUserName;
    private Set<Integer> userId;
    private String reportToUserName;
    private Set<Integer> toUserId;

    private String reportCommentContent;

    private String reason;
    private Integer progressStatus;
    private Integer statusId;

    //起始时间
    private String beginTime;
    //结束时间
    private String endTime;
}
