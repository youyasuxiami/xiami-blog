package com.xiami.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Author：郑锦
 * Date：2020­05­21 0:26
 * Description：<描述>
 */
@Data
public class FrontLoginParam implements Serializable {
    private String name;
    private String password;
    private String isRememberMe;
}
