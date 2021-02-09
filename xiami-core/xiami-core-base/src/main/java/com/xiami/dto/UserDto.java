package com.xiami.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Integer id;
    private String nickName;
    private String password;
    private String name;
    private String sex;
    private String age;
    private String phone;
    private String email;
    private String avatar;
    private Date createTime;
    private Date updateTime;
    private Date loginTime;
    private String ps;
    private String status;
    private Integer[] roleIds;
}