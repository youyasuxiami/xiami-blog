package com.xiami.service;

import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dto.PageRequestDto;
import com.xiami.dto.UserQueryDto;
import com.xiami.entity.User;

import java.io.OutputStream;
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

    /**
     * 新增用户
     * @param user
     * @return
     */
    ResponseResult addUser(User user);

    /**
     * 编辑用户
     * @param user
     * @return
     */
    ResponseResult updateUser(User user);

    /**
     * 启用/禁用账号
     * @param user
     * @return
     */
    ResponseResult updateUserStatus(User user);

    /**
     * 删除用户
     * @param user
     * @return
     */
    ResponseResult deleteUser(User user);

    /**
     * 批量导入excel
     * @param list
     * @return
     */
    ResponseResult importExcel(List list);

    /**
     * 导出功能
     * @param out
     * @param userQueryDto
     * @return
     */
    ResponseResult exportUserToExcel(OutputStream out,UserQueryDto userQueryDto);

}
