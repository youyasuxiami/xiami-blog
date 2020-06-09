package com.xiami.handler;

import com.xiami.base.ErrorCodeEnum;
import com.xiami.base.ResponseResult;
import com.xiami.dto.LoginInfo;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Author：郑锦
 * Date：2020­06­08 23:26
 * Description：<描述>
 */
//这个注解拦截了所有有@Controller的控制器
@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Requst URL : {}，Exception : {}", request.getRequestURL(), e);

        //查找第一个参数类中，是否涵盖ResponseStatus注解。如果有，就返回此注解，没有就返回空
        //if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
        //    throw e;
        //}

        if (e instanceof UnknownAccountException) {//账户不存在（用枚举类型实现）
            return new ResponseResult(ErrorCodeEnum.UNKNOWN_ACCOUNT.getCode(), ErrorCodeEnum.UNKNOWN_ACCOUNT.getMsg());
        }
        if (e instanceof IncorrectCredentialsException) {//密码不正确（用内部类实现）
            return new ResponseResult(ResponseResult.CodeStatus.INCORRECT_CREDENTIALS, "密码不正确");
        }
        if (e instanceof LockedAccountException) {//账户被冻结，请联系管理员（用枚举类型实现）
            return new ResponseResult(ErrorCodeEnum.LOCKED_ACCOUNT.getCode(), ErrorCodeEnum.LOCKED_ACCOUNT.getMsg());
        }

        return new ResponseResult(ErrorCodeEnum.UNKNOWN_EXCEPTION.getCode(), ErrorCodeEnum.UNKNOWN_EXCEPTION.getMsg());//未知异常
    }
}
