package com.xiami.job;

import com.xiami.base.BaseJob;
import com.xiami.entity.PageData;
import com.xiami.service.SysJobService;
import com.xiami.service.SysOperLogService;
import com.xiami.service.TBlogService;
import com.xiami.utils.RedisUtil;
import com.xiami.utils.SpringContextUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.List;

/**
 * Description：获得各个省份中的各个访问量
 *
 * @version v1.0.0
 * @author：zj
 * @date：2021­02-04 12:17
 */
@Slf4j
public class UpdateProvinceAndNum extends BaseJob implements Job {

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) {
        SysJobService sysJobService = SpringContextUtil.getBean(SysJobService.class);

        //业务代码开始
        log.info("更新redis中的各个省份中的各个访问量————开始");
        SysOperLogService sysOperLogService = SpringContextUtil.getBean(SysOperLogService.class);
        List<PageData> provinceAndNum = sysOperLogService.getProvinceAndNum();
        RedisUtil redisUtil = SpringContextUtil.getBean(RedisUtil.class);
        redisUtil.set("provinceAndNum", provinceAndNum);
        log.info("更新redis中的各个省份中的各个访问量————结束");
        //业务代码结束

        String jobName = context.getJobDetail().getKey().getName();//任务名称
        String jobGroup = context.getJobDetail().getKey().getGroup();//任务组名
        Date FireTime = context.getFireTime();
        Date nextFireTime = context.getNextFireTime();
        //更新定时任务中的上次执行时间和下次执行时间
        executeJob(sysJobService, jobName, jobGroup, FireTime, nextFireTime);
    }
}
