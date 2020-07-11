package com.xiami.dao;

import com.xiami.entity.RoleUser;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface RoleUserMapper extends MyMapper<RoleUser> {
    int deleteExist(List<Integer> ids);
}