package com.xiami.exception;

/**
 * Description：定时任务异常类
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­22 23:23
 */
public class QuartzException extends Exception {
    public QuartzException() {
    }

    public QuartzException(String message) {
        super(message);
    }
}
