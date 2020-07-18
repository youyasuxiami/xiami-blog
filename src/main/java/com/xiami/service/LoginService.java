package com.xiami.service;

import java.util.List;

/**
 * Author：郑锦
 * Date：2020­06­01 23:26
 * Description：<描述>
 */
public interface LoginService {

    /**
     * 获得该用户的所有的角色名称
     * @param userName
     * @return
     */
    List<String> getRoleNames(String userName);

    /**
     * 根据用户名获取用户所拥有的菜单url
     * @param name
     * @return
     */
    List<String> getAllMenusByAccount(String name);
}
