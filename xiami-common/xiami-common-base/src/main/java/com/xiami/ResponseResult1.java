package com.xiami;

import java.io.Serializable;

/**
 * Description：这个是商城服务端项目
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­03­28 14:15
 */
public class ResponseResult1 implements Serializable {
    public int code;
    public String message;
    public Object data;

    public ResponseResult1() {
    }

    public ResponseResult1(int code) {
        this.code = code;
    }

    public ResponseResult1(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult1(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult1(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public class CodeStatus {
        /**
         * 请求成功
         */
        public static final int OK = 20000;

        /**
         * 请求失败
         */
        public static final int FAIL = 20004;

        /**
         * 非法请求
         */
        public static final int ILLEGAL_REQUEST = 50000;

        /**
         * 非法的Token
         */
        public static final int ILLEGAL_TOKEN = 50008;

        /**
         * 其它账号已登录
         */
        public static final int OTHER_CLIENTS_LOGGED_IN = 50012;

        /**
         * Token超时
         */
        public static final int TOKEN_EXPIRED = 50014;

        /**
         * 熔断请求
         */
        public static final int BREAKING = 20004;
    }

}
