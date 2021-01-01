package com.xiami.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.xiami.annotation.OperatorLog;
import com.xiami.dao.SysOperLogMapper;
import com.xiami.entity.SysOperLog;
import com.xiami.utils.AddressUtil;
import com.xiami.utils.OperLogUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­27 15:01
 */
@Slf4j
@Aspect
@AllArgsConstructor
@Component
public class OperatorAspect {
    private final TaskExecutor taskExecutor;
    private final JdbcTemplate jdbcTemplate;

    private static HttpServletRequest getCurRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private static long executeTime = 0;

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    @Pointcut("@annotation(com.xiami.annotation.OperatorLog)")
    public void logPointCut() {
    }

    @SneakyThrows
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();

        Long startTime = System.currentTimeMillis();
        Object obj = point.proceed();
        executeTime = System.currentTimeMillis() - startTime;

        log.debug("[类名]:{},[方法]:{},[耗时]:{}", strClassName, strMethodName, executeTime + "毫秒");

        return obj;
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        System.out.println("异常++++++++++");
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
            // 获得注解
            OperatorLog operatorLog = getAnnotationLog(joinPoint);
            if (operatorLog == null) {
                return;
            }

            // 用户名
            String userName = "";
            // 异常信息
            String errorMsg = "";
            if (e != null) {
                errorMsg = e.getMessage();
                HttpServletRequest request = getCurRequest();
                userName = request.getParameter("username");
            } else {
                userName = Objects.requireNonNull(OperLogUtil.getUsername());
            }

            // 终端编号
            String clientId = OperLogUtil.getClientId();

            SysOperLog sysOperLog = new SysOperLog();
            HttpServletRequest request = ((ServletRequestAttributes) Objects
                    .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String ip = ServletUtil.getClientIP(request);
            sysOperLog.setType("1");
            sysOperLog.setTitle(operatorLog.value());
            sysOperLog.setMethod(request.getMethod());
            sysOperLog.setUserAgent(request.getHeader("user-agent"));
            sysOperLog.setOperName(userName);
            sysOperLog.setClientId(clientId);

            String path = URLUtil.getPath(request.getRequestURI());
            sysOperLog.setOperUrl(path);

            sysOperLog.setOperIp(ip);

            sysOperLog.setOperAddr(AddressUtil.getCityInfo(ip));
            sysOperLog.setOperParam(HttpUtil.toParams(request.getParameterMap()));
            if (StrUtil.isNotBlank(errorMsg)) {
                sysOperLog.setStatus(1);
                sysOperLog.setErrorMsg(errorMsg);
            } else {
                sysOperLog.setStatus(0);
                sysOperLog.setErrorMsg(errorMsg);
            }

            sysOperLog.setExecuteTime(userName);
            sysOperLog.setOperTime(new Date());
            int insert = sysOperLogMapper.insert(sysOperLog);
            if (insert > 0) {
                log.info("插入日志成功,操作是{}", operatorLog.value());
            } else {
                log.info("插入日志失败,操作是{}", operatorLog.value());
            }

            //PreparedStatementSetter pss = OperLogUtil.setOperLog(operatorLog.value(), executeTime, userName, clientId, errorMsg);
            //
            //CompletableFuture.runAsync(() -> {
            //    jdbcTemplate.update(SqlConstants.OPER_LOG, pss);
            //}, taskExecutor);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    @SneakyThrows
    private OperatorLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            log.info("存在注解");
            return method.getAnnotation(OperatorLog.class);
        }
        log.info("没有存在注解");
        return null;
    }

}
