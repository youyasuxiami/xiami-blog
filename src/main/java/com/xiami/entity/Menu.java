package com.xiami.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private static final long serialVersionUID = 1L;

    private List<Menu> children = new ArrayList<>();

    public void addChild(Menu menu) {
        children.add(menu);
    }
}