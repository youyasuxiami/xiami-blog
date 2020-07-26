package com.xiami.base;

import lombok.Data;

import java.io.Serializable;

/**
 * Description：<描述>这个是李哥的响应
 *
 * @version v1.0.0
 * @author：zj
 * @date：2019­12­15 22:10
 */
@Data
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 3468352004150968551L;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回对象
     */
    private T data;
    public ResponseResult() {
        super();
    }
    public ResponseResult(Integer code) {
        super();
        this.code = code;
    }
    public ResponseResult(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
    public ResponseResult(Integer code, Throwable throwable) {
        super();
        this.code = code;
        this.message = throwable.getMessage();
    }
    public ResponseResult(Integer code, T data) {
        super();
        this.code = code;
        this.data = data;
    }
    public ResponseResult(Integer code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 根据数据变更条数，返回响应结果
     * @param b
     * @param 提交成功
     * @param 提交失败
     * @return
     */
    public static ResponseResult  getResponseResult(boolean b, String 提交成功, String 提交失败) {
        if (b) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, 提交成功);
            //return null;
        } else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, 提交失败);
        }
    }
    /**
     *
     * Description：状态码
     *
     * @author：zj
     * @version v1.0.0
     * @date：${YEAR}­${MONTH}­${DAY} ${TIME}
     */
    public class CodeStatus{
        /**
         * 成功
         */
        public static final int OK=20000;

        /**
         * 请求失败
         */
        public static final int FAIL=20004;

        /**
         * 非法请求
         */
        public static final int ILLEGAL_REQUEST= 50000;

        /**
         * 非法的Token
         */
        public static final int ILLEGAL_TOKEN=50008;

        /**
         * 其它账号已登录
         */
        public static final int OTHER_CLIENTS_LOGGED_IN=50012;

        /**
         * Token超时
         */
        public static final int TOKEN_EXPIRED =50014;

        /**
         * 验证码错误
         */
        public static final int CODE_ERROR=30000;

        /**
         * 验证码错误
         */
        public static final int INCORRECT_CREDENTIALS=30002;
    }
}
