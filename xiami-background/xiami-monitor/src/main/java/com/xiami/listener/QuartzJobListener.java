package com.xiami.listener;

import com.xiami.config.QuartzConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­19 12:08
 */
//@Configuration
//public class QuartzJobListener implements ApplicationListener {
//    @Override
//    public void onApplicationEvent(ApplicationEvent applicationEvent) {
//        System.out.println("监听");
//    }
//}

@Configuration
public class QuartzJobListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private QuartzConfig quartzConfig;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("Web项目启动===============");
        //quartzConfig.start();
        System.out.println("定时任务已经启动===========");
    }
}
