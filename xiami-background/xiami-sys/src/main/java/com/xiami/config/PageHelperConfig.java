package com.xiami.config;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­31 13:22
 */

import com.github.pagehelper.PageHelper;

import java.util.Properties;

/**
 * 此方案可替代 application.yml中 pagehelper配置
 */
//@Configuration
public class PageHelperConfig {


    //@Bean
    public PageHelper getPageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "false");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

}
