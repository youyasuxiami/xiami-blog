package com.xiami.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­16 22:40
 */
@Data
public class SysJobDto{

    /**
     * 任务id
     */
    private Integer id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 触发器名称
     */
    private String triggerName;

    /**
     * 触发器组别
     */
    private String triggerGroup;

    /**
     * 组内执行顺利，值越大执行优先级越高，最大值9，最小值1
     */
    private String jobOrder;

    /**
     * 1、java类;2、spring bean名称;3、rest调用;4、jar调用;9其他
     */
    //private String jobType;

    /**
     * job_type=3时，rest调用地址，仅支持rest get协议,需要增加String返回值，0成功，1失败;job_type=4时，jar路径;其它值为空
     */
    private String executePath;

    /**
     * 参数值
     */
    private String paramsValue;

    /**
     * cron执行表达式
     */
    private String cronExpression;

    /**
     * 错误策略（1立即执行 2执行一次 3放弃执行）
     */
    private String misfirePolicy;

    /**
     * 1、多租户任务;2、非多租户任务
     */
    private String jobTenantType;

    /**
     * 定时任务状态（未发布、正在运行、暂停、删除）
     */
    private String jobStatusName;

    /**
     * 状态（0正常 1异常）
     */
    private String jobExecuteStatus;

    /**
     * 初次执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 上次执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date previousTime;

    /**
     * 下次执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nextTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String delFlag;


}
