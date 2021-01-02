package com.xiami.service;

import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dto.PageRequestDto;
import com.xiami.dto.UserDto;
import com.xiami.dto.UserQueryDto;
import com.xiami.entity.User;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface UserService {
    /**
     * 获得用户列表数据
     *
     * @return
     */
    List<User> getUsersByPage(PageRequestDto pageRequestDto);

    /**
     * 获取用户列表：方式二
     *
     * @param pageRequestDto
     * @return
     */
    PageResult getUsersByPage1(PageRequestDto pageRequestDto);

    /**
     * 获取用户列表：根据单表条件
     *
     * @param userQueryDto
     * @return
     */
    PageResult getUsersBySearch1(UserQueryDto userQueryDto);

    /**
     * 获取用户列表：根据多表的条件
     *
     * @param userQueryDto
     * @return
     */
    PageResult getUsersBySearch(UserQueryDto userQueryDto);

    /**
     * 新增用户
     *
     * @param userDto
     * @return
     */
    ResponseResult addUser(UserDto userDto);

    /**
     * 编辑用户
     *
     * @param userDto
     * @return
     */
    ResponseResult updateUser(UserDto userDto);

    /**
     * 启用/禁用账号
     *
     * @param user
     * @return
     */
    ResponseResult updateUserStatus(User user);

    /**
     * 删除一个用户
     *
     * @param id
     * @return
     */
    ResponseResult deleteUser(Integer id);

    /**
     * 批量导入excel
     *
     * @param list
     * @return
     */
    ResponseResult importExcel(List<List<Object>> list);

    /**
     * 导出当页数据
     *
     * @param out
     * @param userQueryDto
     * @return
     */
    void exportUserToExcel(OutputStream out, UserQueryDto userQueryDto) throws IOException;

    /**
     * 导出全部数据
     *
     * @param out
     * @param userQueryDto
     * @return
     */
    void exportAllUserToExcel(OutputStream out, UserQueryDto userQueryDto) throws IOException;

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    ResponseResult deleteUsers(Integer[] ids);

    /**
     * 获取所有角色
     *
     * @return
     */
    ResponseResult getRoles();

    /**
     * 获取该用户拥有的所有角色
     *
     * @return
     */
    ResponseResult getCheckedRoles(Integer id);

    /**
     * 新增前台用户
     *
     * @param userDto
     * @return
     */
    ResponseResult addMember(UserDto userDto);

    ResponseResult getUserInfo(Integer blogId);

    /**
     * 根据用户名获取密码
     * @param username
     * @return
     */
    String getPassword(String username);

    /**
     * 根据用户名获取用户
     * @return
     */
    User getUserByName(String name);

    int resetUser(Integer id);
}
