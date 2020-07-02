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
    //用户名
    private String name;
    
    //头像
    private String avatar;

    //角色权限
    private String[] roles;
    //描述
    private String desc;

}
