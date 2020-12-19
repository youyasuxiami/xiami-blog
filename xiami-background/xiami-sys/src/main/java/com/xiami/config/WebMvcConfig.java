package com.xiami.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Author：郑锦
 * Date：2020­06­18 20:47
 * Description：<描述>
 */
//@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //只能下载文件，查看图片，当访问http://localhost:8085/excels/8916.jpg
        registry.addResourceHandler("/excels/**").addResourceLocations("classpath:/static/upload/excels/");
    }
}