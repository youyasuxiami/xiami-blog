package com.xiami.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserInfo {

    private String name;

    private String password;

    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;
}