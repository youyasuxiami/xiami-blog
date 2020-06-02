package com.xiami.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

/**
    * 菜单管理
    */
@Data
@Table(name = "menu")
public class Menu implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 父菜单ID，一级菜单为0
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 菜单名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 菜单URL
     */
    @Column(name = "url")
    private String url;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @Column(name = "perms")
    private String perms;

    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 菜单图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 排序
     */
    @Column(name = "order_num")
    private Integer orderNum;

    private static final long serialVersionUID = 1L;
}