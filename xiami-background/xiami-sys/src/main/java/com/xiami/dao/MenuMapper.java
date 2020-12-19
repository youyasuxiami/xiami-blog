package com.xiami.dao;

import com.xiami.entity.Menu;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface MenuMapper extends MyMapper<Menu> {
    /**
     * 根据用户id获得全部的权限
     * @param userId
     * @return
     */
    List<String> getMenuPerms(Integer userId);

    int updateMenu(Menu menu);

    //删除一个菜单
    int deleteMenu(List<Integer> menuIds);

    /**
     * 获得一个账号的所有一级菜单url
     *
     * @param name
     * @return
     */
    List<Menu> getAllMenusByNameAndType(String name);

    /**
     * 根据用户名。获得全部的菜单（菜单中有权限信息）
     * @param name
     * @return
     */
    List<Menu> getAllMenusByName(String name);


}