package com.xiami.config;

import com.xiami.dto.SysJobForm;
import com.xiami.entity.SysJob;
import com.xiami.exception.QuartzException;
import com.xiami.job.MyJob;
import com.xiami.service.SysJobService;
import com.xiami.utils.QuartzUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­18 12:53
 */
@Slf4j
@Configuration
public class QuartzConfig {

    @Resource
    private Scheduler scheduler;

    @Resource
    private SysJobService sysJobService;

    /**
     * 开启定时任务
     */
    public void start(SysJobForm sysJobForm) throws QuartzException, SchedulerException {

        openJob(sysJobForm);
        //启动任务
        scheduler.start();
    }

    @Autowired
    private ApplicationContext applicationContext;

    @SneakyThrows
    private void openJob(SysJobForm sysJobForm) {
        //quartz三大对象
        String jobName = sysJobForm.getJobName();
        String jobGroup = sysJobForm.getJobGroup();
        String executePath = sysJobForm.getExecutePath();//执行类
        String cronExpression = sysJobForm.getCronExpression();
        String triggerName = sysJobForm.getTriggerName();
        String triggerGroup = sysJobForm.getTriggerGroup();

        //通过反射获取Class
        Class aClass = QuartzUtils.executePathToClazz(executePath);

        //携带额外信息到job，方便注入service
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("applicationContext", applicationContext);
        jobDataMap.put("cronExpression", cronExpression);
        jobDataMap.put("id", sysJobForm.getId());

        //1/创建一个JobDetail
        JobDetail jobDetail = JobBuilder.newJob(aClass)
                .withIdentity(jobName, jobGroup)
                .usingJobData(jobDataMap)
                .build();
        //2、触发器表达式对象
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
        //3、准备一个触发器对象
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroup).withSchedule(cronScheduleBuilder).build();
        //4、开始调度
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    /**
     * 暂停定时任务
     *
     * @param name
     * @param group
     * @throws Exception
     */
    public void pauseJob(String name, String group, Integer id) throws QuartzException, SchedulerException {
        //任务的标志类
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail != null) {
            //暂停某个任务
            scheduler.pauseJob(jobKey);
            //更新任务状态
            SysJob sysJob = new SysJob();
            sysJob.setId(id);
            sysJob.setJobStatus("3");
            sysJob.setNextTime(null);
            sysJobService.updateSysJob(sysJob);
        } else {
            throw new QuartzException("任务不存在");
        }
    }

    /**
     * 查看所有定时任务的基本信息
     * @return
     */
    //public List<TaskInfo> getAllJobsInfo() throws Exception{
    //    List<TaskInfo> list=new ArrayList<TaskInfo>();
    //    //所有任务组
    //    List<String> jobGroupNames = scheduler.getJobGroupNames();
    //    for (String jobGroupName : jobGroupNames) {
    //        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(jobGroupName));
    //        for (JobKey jobKey : jobKeys) {
    //            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
    //            for (Trigger trigger : triggers) {
    //                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
    //                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
    //                String cronExpression=""; //cron表达式
    //                String cronDescription=""; //描述信息
    //                if(trigger instanceof CronTrigger){
    //                    CronTrigger cronTrigger=(CronTrigger)trigger;
    //                    //cron表达式
    //                    cronExpression = cronTrigger.getCronExpression();
    //                    cronDescription=cronTrigger.getDescription();
    //                }
    //                TaskInfo taskInfo=new TaskInfo();
    //                taskInfo.setJobName(jobKey.getName());
    //                taskInfo.setJobGroup(jobKey.getGroup());
    //                taskInfo.setJobDescription(jobDetail.getDescription());
    //                taskInfo.setStatus(triggerState.name()); //任务的状态
    //                taskInfo.setCronExpression(cronExpression);
    //                taskInfo.setCronDescription(cronDescription);
    //                list.add(taskInfo);
    //            }
    //        }
    //    }
    //    return list;
    //}

    /**
     * 04-恢复某个任务的执行
     *
     * @param name
     * @param group
     */
    public void resumeJob(String name, String group, Integer id) throws QuartzException, SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail != null) {
            //恢复某个任务
            scheduler.resumeJob(jobKey);
            //恢复成功，更新任务状态为正在运行状态
            SysJobForm sysJobForm = new SysJobForm();
            sysJobForm.setId(id);
            sysJobForm.setJobStatus("2");
            sysJobService.updateSysJobForm(sysJobForm);
        } else {
            throw new QuartzException("要恢复的任务不存在");
        }
    }

    /**
     * 05-删除某一个任务
     *
     * @param name
     * @param group
     * @throws Exception
     */
    public void deleteJob(String name, String group) throws Exception {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail != null) {
            scheduler.deleteJob(jobKey);
        } else {
            System.out.println("要删除的任务不存在！");
        }
    }

    /**
     * 06-动态的修改任务执行的表达式，触发规则
     *
     * @param name
     * @param group
     * @return
     */
    public boolean modifyJob(String name, String group, String newTime) throws Exception {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        CronTrigger cronTrigger = null;
        if (trigger instanceof CronTrigger) {
            cronTrigger = (CronTrigger) trigger;
            //表达式
            String cronExpression = cronTrigger.getCronExpression();
            if (!cronExpression.equalsIgnoreCase(newTime)) {
                System.out.println("需要修改原来的表达式：" + cronExpression + "为:" + newTime);
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(newTime);
                //新的触发器
                CronTrigger cronTrigger1 = TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(cronScheduleBuilder).build();
                date = scheduler.rescheduleJob(triggerKey, cronTrigger1);
            } else {
                System.out.println("不用修改！和原来的一样！");
            }
        }
        if (date != null) {
            return true;
        } else {
            return false;
        }
    }

}










