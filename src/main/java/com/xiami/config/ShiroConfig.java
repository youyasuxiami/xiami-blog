package com.xiami.config;

import com.xiami.filter.ShiroAuthFilter;
import com.xiami.realm.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­06­02 12:51
 */
@Configuration
public class ShiroConfig {

    //1,创建 SessionManager 管理会话
    @Bean(name = "sessionManager")//<bean class="">
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置过期时间
        sessionManager.setGlobalSessionTimeout(1000*60*30);
        //设置后台线程  清理过期的会话
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置地址比拼接sessionid
        sessionManager.setSessionIdUrlRewritingEnabled(false);

        return sessionManager;
    }
    //2,创建SecurityManager
    @Bean(name="securityManager")
    public SecurityManager securityManager(SessionManager sessionManager, UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);

        //缓存管理
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        securityManager.setCacheManager(cacheManager);

        //cookie管理
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        Cookie cookie = cookieRememberMeManager.getCookie();
        cookie.setMaxAge(60*60*24*3);
        //可以实现session跨多个应用
        cookie.setPath("/");
        securityManager.setRememberMeManager(cookieRememberMeManager);

        //设置自定义realm
        securityManager.setRealm(userRealm);
        return  securityManager;
    }

    public ShiroAuthFilter getShiroAuthFilter(){
        return new ShiroAuthFilter();
    }

    //3,创建ShiroFilter
    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/");
        //shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //shiroFilterFactoryBean.setUnauthorizedUrl("unauthorized.html");
        //拦截的路径的详细设置
        //什么Map是存取有序的？

        // 自定义的登录过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("ShiroAuthFilter", getShiroAuthFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        Map<String,String> map = new LinkedHashMap<>();
        map.put("/login","anon");//匿名访问
        map.put("/getPublicKey","anon");//公钥放行
        map.put("/info","anon");//匿名访问
        map.put("/logout","anon");//匿名访问
        map.put("/captcha.jpg","anon");//验证码放行
        map.put("/**","ShiroAuthFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return  shiroFilterFactoryBean;
    }

    //4,BeanLifeCycle  生命周期
    @Bean(name="lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return  lifecycleBeanPostProcessor;
    }

    //5,开启shiro的注解
    @Bean(name = "defaultAdvisorAutoProxyCreator")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);//cglib方式
        return  defaultAdvisorAutoProxyCreator;
    }

    @Bean(name="authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
