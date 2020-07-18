package com.xiami.dao;

import java.util.List;

public interface LoginMapper  {
    List<String> getRoleNames(String username);

    /**
     * 获得一个账号的所有菜单url
     * @param name
     * @return
     */
    List<String> getAllMenusByName(String name);
}