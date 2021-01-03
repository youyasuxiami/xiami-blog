package com.xiami.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "sys_oper_log")
public class SysOperLog implements Serializable {
    /**
     * 日志编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 日志类型
     */
    @Column(name = "`type`")
    private String type;

    /**
     * 日志标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 方法名称
     */
    @Column(name = "`method`")
    private String method;

    /**
     * 用户代理
     */
    @Column(name = "user_agent")
    private String userAgent;

    /**
     * 操作人员
     */
    @Column(name = "oper_name")
    private String operName;

    /**
     * 终端
     */
    @Column(name = "client_id")
    private String clientId;

    /**
     * 请求URL
     */
    @Column(name = "oper_url")
    private String operUrl;

    /**
     * 主机地址
     */
    @Column(name = "oper_ip")
    private String operIp;

    /**
     * 操作地点
     */
    @Column(name = "oper_addr")
    private String operAddr;

    /**
     * 请求参数
     */
    @Column(name = "oper_param")
    private String operParam;

    /**
     * 操作状态（0正常 1异常）
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 错误消息
     */
    @Column(name = "error_msg")
    private String errorMsg;

    /**
     * 执行时间
     */
    @Column(name = "execute_time")
    private String executeTime;

    /**
     * 操作时间
     */
    @Column(name = "oper_time")
    private Date operTime;

    @JsonIgnore
    private String[] createTime;


    private static final long serialVersionUID = 1L;
}