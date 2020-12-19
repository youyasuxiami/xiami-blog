package com.xiami;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­19 23:00
 */
@SpringBootApplication(scanBasePackages = "com.xiami")
@MapperScan("com.xiami.dao")
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class,args);
    }
}
