package com.xiami.dao;

import com.xiami.dto.UserQueryDto;
import com.xiami.entity.SysDictionary;
import com.xiami.entity.User;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface UserMapper extends MyMapper<User> {
    List<User> selectByQuery(UserQueryDto userQueryDto);

}