package com.xiami.config;

import com.xiami.filter.JWTFilter;
import com.xiami.filter.ShiroFilterConfig;
import com.xiami.realm.UserRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean  //不指定名字的话，自动创建一个方法名第一个字母小写的bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        //filterMap.put(shiroFilterConfig.getAuthc(), new JWTFilter(aepAuthConfig, authService));
        //shiroFilterFactoryBean.setFilters(filterMap);
        //shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterConfig.getMap());

        //将JWTFilter命名为jwt
        filterMap.put("jwt",new JWTFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        //设置无权限时跳转的url
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized/无权限");
        //shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setLoginUrl("/");
        Map<String,String> map = new HashMap<>(2);
        //所有请求通过我们自己的过滤器
        map.put("/index/**", "anon");//匿名访问
        map.put("/login/**", "anon");//匿名访问
        map.put("/content/**", "anon");//匿名访问
        map.put("/login", "anon");//匿名访问
        map.put("/getPublicKey", "anon");//公钥放行
        map.put("/info", "anon");//匿名访问
        map.put("/logout", "anon");//匿名访问
        map.put("/captcha.jpg", "anon");//验证码放行

        map.put("/**","jwt");
        //匿名用户可以访问的url
        map.put("/unauthorized/**","anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    @Bean
    public SecurityManager securityManager(UserRealm myRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}