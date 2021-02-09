package com.xiami.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­25 20:41
 */
//使得Spring可以在项目启动时加载 SpringContextUtil ，完成 applicationContext 的初始化。
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context; // Spring应用上下文环境

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (context == null) {
            context = applicationContext;
        }
    }

    // 获得applicationContext
    public static ApplicationContext getApplicationContext() throws Exception {
        isContext();
        return context;
    }

    // 通过class获取Bean
    public static <T> T getBean(Class<T> clazz) throws Exception {
        return getApplicationContext().getBean(clazz);
    }

    // 通过name获取 Bean
    public static Object getBean(String name) throws Exception {
        return getApplicationContext().getBean(name);
    }

    // 判断application是否为空
    public static void isContext() throws Exception {
        if (context == null) {
            throw new Exception("application未注入");
        }
    }
}
