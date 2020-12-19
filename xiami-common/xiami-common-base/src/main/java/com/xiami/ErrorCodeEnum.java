package com.xiami;


/**
 * Author：郑锦
 * Date：2020­06­08 23:28
 * Description：<描述>
 */
public enum ErrorCodeEnum {

    // 基础错误异常
    UNKNOWN_ACCOUNT(30001, "账户不存在"),
    INCORRECT_CREDENTIALS(30002, "密码不正确"),
    LOCKED_ACCOUNT(30002, "账户被冻结，请联系管理员"),
    UNKNOWN_EXCEPTION(31000, "未知异常"),


    INVALID_PARAMS(1, "非法参数"),
    PROC_FAILED(2, "处理失败"),
    DATA_NOT_FOUND(3, "数据不存在"),
    DATA_EMPTY(4, "数据为空"),
    ;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
