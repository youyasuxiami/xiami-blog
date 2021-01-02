package com.xiami.utils;

import com.xiami.entity.User;
import com.xiami.jwt.AuthUtils;
import com.xiami.jwt.BaseJwtInfo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­11­21 10:09
 */
@Component
public class UserUtils implements ApplicationContextAware {

    private static String tokenHeader;

    private static AuthUtils authUtils;

     private static HttpServletRequest getCurRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取token解析用户存储数据
     *
     * @return
     */
    public static BaseJwtInfo getJWTInfo() {
        HttpServletRequest request = getCurRequest();
        BaseJwtInfo baseJwtInfo;
        try {
            //infoFromToken = aepAuthUtil.getInfoFromToken(request.getHeader(tokenHeader));
            String token=request.getHeader(tokenHeader);
            //String token=request.getHeader("Authorization");
            //String username = JWTUtil.getUsername(token);
            baseJwtInfo=authUtils.getBaseJwtInfo(token);
            return  baseJwtInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User getUser() {
        HttpServletRequest request = getCurRequest();
        User user;
        try {
            String token=request.getHeader(tokenHeader);
            if(!"undefined".equals(token)&&token!=null){
                user=authUtils.getUser(token);
                return user;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //@PostConstruct
    //public void init(){
    //    userUtils=this;
    //    userUtils.userMapper=this.userMapper;
    //}


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        tokenHeader = applicationContext.getEnvironment().getProperty("auth.token-header");
        //userMapper = applicationContext.getBean(UserMapper.class);
        authUtils = applicationContext.getBean(AuthUtils.class);
        //aepAuthUtil = applicationContext.getBean(AepAuthUtil.class);
    }
}
