package com.xiami.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­16 22:21
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@EnableScheduling
public class TestJob {

    @Scheduled(cron = "10 * * * * ?")
    public void say(){
        System.out.println("----------");
        System.out.println("我喜欢你，袁嘉儿");
    }

}
