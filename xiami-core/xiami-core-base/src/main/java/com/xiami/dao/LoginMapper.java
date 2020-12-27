package com.xiami.dao;

import java.util.List;
import java.util.Map;

public interface LoginMapper {
    List<String> getRoleNames(String username);

    /**
     * 获得一个账号的所有菜单url
     *
     * @param map
     * @return
     */
    List<String> getAllMenusByName(Map map);
}