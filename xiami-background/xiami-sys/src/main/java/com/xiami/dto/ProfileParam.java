package com.xiami.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­24 21:54
 */
@Data
public class ProfileParam implements Serializable {
    private static final long serialVersionUID = -8472527239027808727L;

    /**
     * 用户编号
     */
    private Integer id;

    private String nickName;

    /**
     * 密码
     */
    private String password;

    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private String age;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 最新登录时间
     */
    private Date loginTime;

    /**
     * 备注
     */
    private String ps;

    /**
     * 账号状态
     */
    private String status;
}
