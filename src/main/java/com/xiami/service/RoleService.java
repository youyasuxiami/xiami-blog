package com.xiami.service;

import java.util.List;

/**
 * Author：郑锦
 * Date：2020­06­01 23:26
 * Description：<描述>
 */
public interface RoleService {

    //获得所有的角色名称
    List<String> getRoleNames(Integer userId);
}
