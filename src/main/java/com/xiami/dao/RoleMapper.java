package com.xiami.dao;

import com.xiami.entity.Role;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface RoleMapper extends MyMapper<Role> {
    List<String> getRoleNames(Integer userId);
}