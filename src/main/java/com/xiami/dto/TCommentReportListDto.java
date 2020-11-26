package com.xiami.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­01 0:08
 */
@Data
public class TCommentReportListDto {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 举报人id
     */
    private Integer reportUserId;

    /**
     * 举报人用户名
     */
    private String reportUserName;

    /**
     * 被举报人id
     */
    private Integer reportToUserId;


    /**
     * 被举报人用户名
     */
    private String reportToUserName;

    /**
     * 被举报的评论id
     */
    private Integer reportCommentId;

    /**
     * 被举报的评论的内容
     */
    private String reportCommentContent;

    private String reason;

    private Integer progressStatus;

    private String progressStatusName;


    /**
     * 举报时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
