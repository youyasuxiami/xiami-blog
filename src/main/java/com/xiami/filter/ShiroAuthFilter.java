package com.xiami.filter;

import com.xiami.utils.MapperUtils;
import com.xiami.utils.ShiroUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­06­06 18:55
 */
public class ShiroAuthFilter extends FormAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroAuthFilter.class);


    public ShiroAuthFilter() {
        super();
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //Always return true if the request's method is OPTIONS
        //if (request instanceof HttpServletRequest) {
        //    String s = ((HttpServletRequest) request).getMethod().toUpperCase();
        //    //application/json时，OPTIONS表示的是没有cookie
        //    if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
        //        return true;
        //    }
        //}


        //Subject subject = this.getSubject(request, response);
        //subject.login(token);
        //subject.isAuthenticated()将等于true，则表示已经这个账号已经登录成功了
        //如果执行了subject.login(token);那么subject.isAccessAllowed等于true
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Session session = ShiroUtils.getSession();


        logger.info("SHIROFILTER authc拦截");
        HttpServletResponse res = (HttpServletResponse)response;
        res.setHeader("Access-Control-Allow-Origin", "true");
        res.setContentType("application/json; charset=utf-8");
        res.setStatus(HttpServletResponse.SC_OK);


        PrintWriter writer = res.getWriter();
        Map<String, Object> map= new HashMap<>();
        map.put("status", 3);
        map.put("msg", "未登录");
        writer.write(MapperUtils.mapToJson(map));
        writer.close();
        //return false 拦截， true 放行
        return false;
    }
}
