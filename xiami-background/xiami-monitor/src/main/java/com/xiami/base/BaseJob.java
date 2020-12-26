package com.xiami.base;

import com.xiami.dao.SysJobMapper;
import com.xiami.entity.SysJob;
import com.xiami.service.SysJobService;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­26 8:56
 */
@Slf4j
public class BaseJob {

    /**
     * 更新数据库中的上一次执行时间、下次执行时间
     * @param sysJobMapper
     * @param jobName
     * @param jobGroup
     * @param fireTime
     * @param nextFireTime
     */
    public void executeJob(SysJobService sysJobService, String jobName, String jobGroup, Date fireTime, Date nextFireTime) {
        SysJob sysJob=new SysJob();
        sysJob.setJobName(jobName);
        sysJob.setJobGroup(jobGroup);
        sysJob.setPreviousTime(fireTime);
        sysJob.setNextTime(nextFireTime);
        sysJobService.updateTimes(sysJob);
    }
}
