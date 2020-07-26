package com.xiami.dao;

import com.xiami.entity.Menu;
import tk.mybatis.mapper.MyMapper;import java.util.List;

public interface MenuMapper extends MyMapper<Menu> {
    List<String> getMenuPerms(Integer userId);

    int updateMenu(Menu menu);

    //删除一个菜单
    int deleteMenu(List<Integer> menuIds);

    /**
     * 获得一个账号的所有菜单url
     * @param name
     * @return
     */
    List<Menu> getAllMenusByName(String name);
}