package com.xiami.dao;

import com.xiami.entity.RolePermission;
import com.xiami.entity.User;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface RolePermissionMapper extends MyMapper<RolePermission> {
    int  insertRolePermission(List<RolePermission> list);
}