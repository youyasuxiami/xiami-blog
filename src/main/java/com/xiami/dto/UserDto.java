package com.xiami.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto  {
    private Integer id;
    private String nickName;
    private String password;
    private String name;
    private String sex;
    private String age;
    private String phone;
    private String email;
    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;
    private String ps;
    private String status;
    private Integer[] roleIds;
}