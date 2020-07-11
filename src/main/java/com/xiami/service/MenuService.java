package com.xiami.service;

import com.xiami.base.ResponseResult;
import com.xiami.entity.Menu;
import com.xiami.entity.User;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­06­02 23:13
 */
public interface MenuService {
    ResponseResult getMenuJsonList();

    /**
     * 新增用户
     * @param menu
     * @return
     */
    ResponseResult addMenu(Menu menu);

    /**
     * 编辑用户
     * @param menu
     * @return
     */
    ResponseResult updateMenu(Menu menu);

    /**
     * 删除一个菜单
     * @param id
     * @return
     */
    ResponseResult deleteMenu(Integer id);
}

