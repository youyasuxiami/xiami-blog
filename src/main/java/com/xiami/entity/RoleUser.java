package com.xiami.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "role_user")
public class RoleUser implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 角色编号
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}