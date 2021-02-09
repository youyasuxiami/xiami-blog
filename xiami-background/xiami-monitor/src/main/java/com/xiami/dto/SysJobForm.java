package com.xiami.dto;

import lombok.Data;

import java.util.Date;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­20 21:31
 */
@Data
public class SysJobForm {
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
     * job_type=3时，rest调用地址，仅支持rest get协议,需要增加String返回值，0成功，1失败;job_type=4时，jar路径;其它值为空
     */
    private String executePath;

    /**
     * cron执行表达式
     */
    private String cronExpression;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 触发器名称
     */
    private String triggerName;

    /**
     * 触发器组别
     */
    private String triggerGroup;

    /**
     * 定时任务状态
     */
    private String jobStatus;

    private String nextTime;


}
