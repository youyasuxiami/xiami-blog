package com.xiami.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "`user`")
public class User implements Serializable {
    /**
     * 用户编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "nick_name")
    private String nickName;

    /**
     * 密码
     */
    @Column(name = "`password`")
    private String password;

    @Column(name = "`name`")
    private String name;

    /**
     * 性别
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 年龄
     */
    @Column(name = "age")
    private String age;

    /**
     * 联系方式
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 最新登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;

    private static final long serialVersionUID = 1L;
}