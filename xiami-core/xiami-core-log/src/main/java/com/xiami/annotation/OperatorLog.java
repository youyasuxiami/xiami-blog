package com.xiami.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description：日志注解，切面切入点的标识
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­27 15:11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperatorLog {
    /**
     * 描述
     *
     * @return {String}
     */
    String value();
}
