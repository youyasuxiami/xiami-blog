package com.xiami.common;
import java.io.Serializable;

/**
 * @Description 统一反参对象
 * @Author Ivan Lee
 * @Date 12:33 2019/8/30
 */
public class ApiBaseResponse<T> extends BaseResponse implements Serializable {

    /**
     * 结果数据
     */
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ApiBaseResponse<T> msg(String message) {
        super.setMessage(message);
        return this;
    }

    public ApiBaseResponse<T> data(T data) {
        this.setData(data);
        return this;
    }

    public static <T> ApiBaseResponse<T> success(T data) {
        ApiBaseResponse<T> apiBaseResponse = new ApiBaseResponse();
        apiBaseResponse.setData(data);
        return apiBaseResponse;
    }

    public static <T> ApiBaseResponse fault(int status, String message) {
        ApiBaseResponse apiBaseResponse = new ApiBaseResponse();
        apiBaseResponse.setStatus(status);
        apiBaseResponse.setMessage(message);
        return apiBaseResponse;
    }

}
