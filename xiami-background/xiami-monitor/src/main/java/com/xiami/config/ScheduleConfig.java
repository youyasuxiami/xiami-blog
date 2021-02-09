package com.xiami.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­19 12:44
 */
@Configuration
public class ScheduleConfig {
    /**
     * 01- 需要把quartz配置注入
     * @return
     * @throws Exception
     */
    @Bean(name="quartzProperties")
    public Properties quartzProperties() throws Exception{
        PropertiesFactoryBean propertiesFactoryBean=new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //重新初始化对象，并把对象返回
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(Properties quartzProperties)throws Exception{
        SchedulerFactoryBean schedulerFactoryBean=new SchedulerFactoryBean();
        schedulerFactoryBean.setQuartzProperties(quartzProperties);
        return schedulerFactoryBean;
    }
    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean){
        return schedulerFactoryBean.getScheduler();
    }

}
