package com.xiami.dao;

import com.xiami.dto.UserQueryDto;
import com.xiami.entity.User;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface UserMapper extends MyMapper<User> {
    List<User> selectByQuery(UserQueryDto userQueryDto);

    //批量导入用户
    int  insertUsers(List<User> list);

    //批量删除用户
    int deleteUsers(Integer[] ids);


}