package com.xiami.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Description：登录信息
 *
 * @version v1.0.0
 * @author：zj
 * @date：2019­12­18 22:27
 */
@Data
public class LoginInfo implements Serializable {
    private String name;

    private String avatar;
}
