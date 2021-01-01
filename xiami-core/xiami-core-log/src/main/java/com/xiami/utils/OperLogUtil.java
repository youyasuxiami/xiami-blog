package com.xiami.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.xiami.entity.User;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author entfrm
 * @date 2020/3/18
 * @description 操作日志util
 */
@Slf4j
@UtilityClass
public class OperLogUtil {

    public PreparedStatementSetter setOperLog(String title, long time, String userName, String clientId, String errorMsg) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = ServletUtil.getClientIP(request);
        return new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, "1");
                ps.setString(2, title);
                ps.setString(3, request.getMethod());
                ps.setString(4, request.getHeader("user-agent"));
                ps.setString(5, userName);
                ps.setString(6, clientId);
                String path = URLUtil.getPath(request.getRequestURI());
                ps.setString(7, path);
                ps.setString(8, ip);
                String cityInfo = AddressUtil.getCityInfo(ip);
                ps.setString(9, cityInfo);
                String s = HttpUtil.toParams(request.getParameterMap());
                ps.setString(10, s);
                if (StrUtil.isNotBlank(errorMsg)) {
                    ps.setString(11, "1");
                    ps.setString(12, errorMsg);
                } else {
                    ps.setString(11, "0");
                    ps.setString(12, "");
                }
                ps.setString(13, time + "");
                ps.setString(14, DateUtil.now());
            }
        };
    }

    /**
     * 获取客户端
     *
     * @return clientId
     */
    public String getClientId() {
        User user = UserUtils.getUser();
        String id = String.valueOf(user.getId());
        return id;
    }

    /**
     * 获取用户名称
     *
     * @return username
     */
    public String getUsername() {
        User user = UserUtils.getUser();
        String userName = user.getName();
        return userName;
    }
}
