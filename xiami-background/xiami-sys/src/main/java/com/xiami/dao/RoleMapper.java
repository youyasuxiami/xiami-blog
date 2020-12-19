package com.xiami.dao;

import com.xiami.entity.Role;
import tk.mybatis.mapper.MyMapper;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends MyMapper<Role> {
    List<String> getRoleNames(Integer userId);

    List<Map<String, Object>> selectRoles();

    /**
     * 根据用户名获得全部角色
     * @param name
     * @return
     */
    List<Role> getAllRolesByName(String name);
}