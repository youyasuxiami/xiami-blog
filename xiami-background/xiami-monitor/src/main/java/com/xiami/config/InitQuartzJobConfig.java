package com.xiami.config;

import com.xiami.dao.SysJobMapper;
import com.xiami.enums.QuartzStatus;
import com.xiami.service.SysJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description：启动项目的时候，原来项目是什么状态，就保持什么状态
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­26 16:47
 */
@Configuration
public class InitQuartzJobConfig {

    @Autowired
    private SysJobMapper sysJobMapper;

    @Autowired
    private SysJobService sysJobService;

    @Autowired
    private QuartzConfig quartzConfig;

    @Bean
    public void customize() {
        sysJobMapper.selectAll().forEach(sysJob -> {
            //2：正在运行
            if(sysJob.getJobStatus().equals(QuartzStatus.JOB_STATUS_RUNNING.getType())){
                //更新运行的定时任务状态为1：未发布
                sysJob.setJobStatus("1");
                sysJobService.updateSysJob(sysJob);
            }
        });
    }
}
