package com.xiami.service;

import com.xiami.base.PageResult;
import com.xiami.dto.PageRequestDto;
import com.xiami.dto.UserQueryDto;
import com.xiami.entity.User;

import java.util.List;

public interface UserService {
    /**
     * 获得用户列表数据
     * @return
     */
    List<User> getUsersByPage(PageRequestDto pageRequestDto);

    /**
     * 获取用户列表：方式二
     * @param pageRequestDto
     * @return
     */
    PageResult getUsersByPage1(PageRequestDto pageRequestDto);

    /**
     * 获取用户列表：根据单表条件
     * @param userQueryDto
     * @return
     */
    PageResult getUsersBySearch1(UserQueryDto userQueryDto);

    /**
     * 获取用户列表：根据多表的条件
     * @param userQueryDto
     * @return
     */
    PageResult getUsersBySearch(UserQueryDto userQueryDto);

    int addUser(User user);
}
