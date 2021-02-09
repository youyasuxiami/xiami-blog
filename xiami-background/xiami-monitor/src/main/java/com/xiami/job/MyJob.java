package com.xiami.job;

import com.xiami.dao.SysJobMapper;
import com.xiami.dto.SysJobForm;
import com.xiami.entity.SysJob;
import com.xiami.service.OrderService;
import com.xiami.utils.QuartzUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­18 12:50
 */
@Slf4j
public class MyJob implements Job {
    //不能这样注入
    //@Autowired
    //private OrderService orderService;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context)  {
        //获取传递过来的jobDataMap
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

        //从Spring容器中获取对象，从IOC中获取对象，通过这种方式注入组件
        ApplicationContext applicationContext = (ApplicationContext) jobDataMap.get("applicationContext");
        OrderService orderService = applicationContext.getBean(OrderService.class);
        orderService.sayHi();

        //当前执行的时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = simpleDateFormat.format(new Date());//2020-12-22 09:14:30

        //Date nextFireTime = context.getNextFireTime();
        //String nextFireTimeStr1 = simpleDateFormat.format(nextFireTime);//2020-12-22 09:15:00


        //更新定时任务中的上次执行时间和下次执行时间
        SysJobMapper sysJobMapper = applicationContext.getBean(SysJobMapper.class);
        String cronExpression = (String) jobDataMap.get("cronExpression");
        String id = String.valueOf(jobDataMap.get("id"));
        CronExpression cronExpression1 = new CronExpression(cronExpression);
        Date next = cronExpression1.getNextValidTimeAfter(new Date());
        //下次执行的时间
        SysJob sysJob=new SysJob();
        sysJob.setId(Integer.valueOf(id));
        sysJob.setPreviousTime(new Date());
        sysJob.setNextTime(next);
        sysJobMapper.updateByPrimaryKeySelective(sysJob);

        log.info("本次执行时间：" + time);//也就是定时任务中的上次执行时间
        log.info("下次执行时间：" + simpleDateFormat.format(next));//也就是定时任务中的下次执行时间
    }


}
