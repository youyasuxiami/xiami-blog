package com.xiami.service;

import com.xiami.base.ResponseResult;
import com.xiami.dto.RoleParam;

import java.util.List;

/**
 * Author：郑锦
 * Date：2020­06­01 23:26
 * Description：<描述>
 */
public interface RoleService {

    /**
     * 获得所有的角色名称
     * @param userId
     * @return
     */
    List<String> getRoleNames(Integer userId);

    /**
     * 获取所有的角色
     * @return
     */
    ResponseResult getRoleList();

    /**
     * 添加角色
     * @param param
     * @return
     */
    ResponseResult addRole(RoleParam param);

    /**
     * 根据角色id获取菜单
     * @param id
     * @return
     */
    ResponseResult getMenusByRoleId(Integer id);

    /**
     * 编辑角色
     * @param param
     * @return
     */
    ResponseResult updateRole(RoleParam param);

}
