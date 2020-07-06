package com.xiami.dao;

import com.xiami.entity.Role;
import com.xiami.entity.User;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface RoleMapper extends MyMapper<Role> {
    List<String> getRoleNames(Integer userId);

}