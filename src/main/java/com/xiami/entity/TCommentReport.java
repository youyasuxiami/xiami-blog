package com.xiami.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "t_comment_report")
public class TCommentReport implements Serializable {
    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 举报人id
     */
    @Column(name = "report_user_id")
    private Integer reportUserId;

    /**
     * 被举报人id
     */
    @Column(name = "report_to_user_id")
    private Integer reportToUserId;

    /**
     * 被举报的评论id
     */
    @Column(name = "report_comment_id")
    private Integer reportCommentId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "progress_status")
    private Integer progressStatus;

    /**
     * 举报时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}