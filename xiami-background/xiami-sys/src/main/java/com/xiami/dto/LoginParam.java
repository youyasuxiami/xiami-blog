package com.xiami.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Author：郑锦
 * Date：2020­05­21 0:26
 * Description：<描述>
 */
@Data
public class LoginParam implements Serializable {
    private String username;
    private String password;
    private String captcha;
}
