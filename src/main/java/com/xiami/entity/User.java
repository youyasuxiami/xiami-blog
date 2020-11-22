package com.xiami.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 最新登录时间
     */
    @Column(name = "login_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /**
     * 备注
     */
    @Column(name = "`ps`")
    private String ps;

    /**
     * 账号状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 账号状态
     */
    @Column(name = "ali_pay")
    private String aliPay;

    /**
     * 账号状态
     */
    @Column(name = "weixin_pay")
    private String weixinPay;

    private List<Role> roleList;

    private static final long serialVersionUID = 1L;
}