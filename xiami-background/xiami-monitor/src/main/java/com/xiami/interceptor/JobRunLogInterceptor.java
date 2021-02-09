package com.xiami.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description： 对Job(任务)建立一个监听器
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­26 11:08
 */
@Slf4j
public class JobRunLogInterceptor implements JobListener{
    /**
     * 用于获取该JobListener的名称
     * @return
     */
    @Override
    public String getName() {
        String name = getClass().getSimpleName();
        log.info("监听器的名称为： "+name);
        return name;
    }

    /**
     * Scheduler在JobDetail将要被执行时调用这个方法
     * @param context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        log.info("被监听的定时任务job的name是："+jobName+"   Scheduler在JobDetail将要被执行时触发该方法");
    }

    /**
     * Scheduler在JobDetail即将被执行，但又被TriggerListerner否决时会调用该方法
     * @param context
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String name = context.getJobDetail().getKey().getName(); //获取Job的name
        log.info("Scheduler在JobDetail即将执行，但又被TriggerListerner否决时会调用的方法");
    }

    /**
     * Scheduler在JobDetail被执行之后调用这个方法
     * @param context
     * @param jobException
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        //获得任务名称
        String jobName = context.getJobDetail().getKey().getName();
        //耗时时间
        long runtime = context.getJobRunTime();
        Date fireTime = context.getFireTime();
        Date nextFireTime = context.getNextFireTime();
        log.info("Scheduler是在JobDetail被执行之后调用这个方法的");
        log.info("定时任务: " + jobName + " 执行完毕，共耗时：" + runtime+"ms");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        log.info("本次执行时间：" + simpleDateFormat.format(fireTime));//也就是定时任务中的上次执行时间
        log.info("下次执行时间：" + simpleDateFormat.format(nextFireTime));//也就是定时任务中的下次执行时间
    }
}
