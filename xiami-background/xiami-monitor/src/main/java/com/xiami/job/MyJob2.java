package com.xiami.job;

import com.xiami.base.BaseJob;
import com.xiami.dao.SysJobMapper;
import com.xiami.entity.SysJob;
import com.xiami.service.OrderService;
import com.xiami.service.SysJobService;
import com.xiami.utils.QuartzUtils;
import com.xiami.utils.SpringContextUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.core.jmx.JobDataMapSupport;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­18 12:50
 */
@Slf4j
public class MyJob2 extends BaseJob implements Job {

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) {
        SysJobService sysJobService = SpringContextUtil.getBean(SysJobService.class);

        String jobName = context.getJobDetail().getKey().getName();//任务名称
        String jobGroup = context.getJobDetail().getKey().getGroup();//任务组名
        Date FireTime = context.getFireTime();
        Date nextFireTime = context.getNextFireTime();
        //更新定时任务中的上次执行时间和下次执行时间
        executeJob(sysJobService, jobName, jobGroup, FireTime, nextFireTime);
    }
}
