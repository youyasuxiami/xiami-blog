package com.xiami.common;

/**
 * Author：郑锦
 * Date：2019­02­20 14:16
 * Description：<描述>
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    //对用户进行分组
    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;//管理员
    }

    //根据type的取值是email还是username，从而判断邮箱或者用户名是否存在
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
}
