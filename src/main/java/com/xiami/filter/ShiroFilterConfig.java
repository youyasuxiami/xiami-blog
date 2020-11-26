package com.xiami.filter;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­11­22 23:20
 */
//@Component
public class ShiroFilterConfig implements ApplicationContextAware {

    private String anon;

    private String authc;

    private Map<String, String> map = new LinkedHashMap<>();

    public String getAnon() {
        return anon;
    }

    public void setAnon(String anon) {
        this.anon = anon;
    }

    public String getAuthc() {
        return authc;
    }

    public void setAuthc(String authc) {
        this.authc = authc;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        anon = applicationContext.getEnvironment().getProperty("shiro-filter-config.anon");
        authc = applicationContext.getEnvironment().getProperty("shiro-filter-config.authc");
        applicationContext.getEnvironment().getProperty("shiro-filter-config.anons").split(",");
        for (String s : applicationContext.getEnvironment().getProperty("shiro-filter-config.anons").split(",")) {
            map.put(s,anon);
        }
        for (String s : applicationContext.getEnvironment().getProperty("shiro-filter-config.authcs").split(",")) {
            map.put(s,authc);
        }
    }
}