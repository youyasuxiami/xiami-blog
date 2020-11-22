package com.xiami.exception;


/**
 * Created by ace on 2017/9/8.
 */
public class UserTokenException extends RuntimeException {
    private Integer code;
    private String message;

    public UserTokenException() {
    }

    public UserTokenException(String message) {
        this.message = message;
    }

    public UserTokenException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
