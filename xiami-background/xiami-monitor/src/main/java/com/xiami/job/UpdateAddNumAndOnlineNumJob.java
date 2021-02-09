package com.xiami.job;

import com.xiami.base.BaseJob;
import com.xiami.service.SysJobService;
import com.xiami.service.SysOperLogService;
import com.xiami.service.UserService;
import com.xiami.utils.RedisUtil;
import com.xiami.utils.SpringContextUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Date;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­18 12:50
 */
@Slf4j
public class UpdateAddNumAndOnlineNumJob extends BaseJob implements Job {

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) {
        SysJobService sysJobService = SpringContextUtil.getBean(SysJobService.class);

        //业务代码开始
        log.info("定时任务UpdateAddNumAndOnlineNumJob，更新redis中的本月新增用户数————开始");
        UserService userService = SpringContextUtil.getBean(UserService.class);
        RedisUtil redisUtil = SpringContextUtil.getBean(RedisUtil.class);
        redisUtil.set("addNum", userService.getAddNum());
        log.info("定时任务UpdateAddNumAndOnlineNumJob，更新redis中的本月新增用户数————结束");
        //业务代码结束

        String jobName = context.getJobDetail().getKey().getName();//任务名称
        String jobGroup = context.getJobDetail().getKey().getGroup();//任务组名
        Date FireTime = context.getFireTime();
        Date nextFireTime = context.getNextFireTime();
        //更新定时任务中的上次执行时间和下次执行时间
        executeJob(sysJobService, jobName, jobGroup, FireTime, nextFireTime);
    }
}
