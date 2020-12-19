package com.xiami.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "sys_job")
public class SysJob implements Serializable {
    /**
     * 任务id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 任务名称
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * 任务组名
     */
    @Column(name = "job_group")
    private String jobGroup;

    /**
     * 组内执行顺利，值越大执行优先级越高，最大值9，最小值1
     */
    @Column(name = "job_order")
    private String jobOrder;

    /**
     * 1、java类;2、spring bean名称;3、rest调用;4、jar调用;9其他
     */
    @Column(name = "job_type")
    private String jobType;

    /**
     * job_type=3时，rest调用地址，仅支持rest get协议,需要增加String返回值，0成功，1失败;job_type=4时，jar路径;其它值为空
     */
    @Column(name = "execute_path")
    private String executePath;

    /**
     * 参数值
     */
    @Column(name = "params_value")
    private String paramsValue;

    /**
     * cron执行表达式
     */
    @Column(name = "cron_expression")
    private String cronExpression;

    /**
     * 错误策略（1立即执行 2执行一次 3放弃执行）
     */
    @Column(name = "misfire_policy")
    private String misfirePolicy;

    /**
     * 1、多租户任务;2、非多租户任务
     */
    @Column(name = "job_tenant_type")
    private String jobTenantType;

    /**
     * 状态（1、未发布;2、运行中;3、暂停;4、删除;）
     */
    @Column(name = "job_status")
    private String jobStatus;

    /**
     * 状态（0正常 1异常）
     */
    @Column(name = "job_execute_status")
    private String jobExecuteStatus;

    /**
     * 初次执行时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 上次执行时间
     */
    @Column(name = "previous_time")
    private Date previousTime;

    /**
     * 下次执行时间
     */
    @Column(name = "next_time")
    private Date nextTime;

    /**
     * 创建者
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 备注信息
     */
    @Column(name = "remarks")
    private String remarks;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @Column(name = "del_flag")
    private String delFlag;

    private static final long serialVersionUID = 1L;
}