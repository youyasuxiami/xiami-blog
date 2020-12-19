package com.xiami.service.impl;

import com.xiami.dao.LoginMapper;
import com.xiami.dao.MenuMapper;
import com.xiami.entity.Menu;
import com.xiami.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> getRoleNames(String userName) {
        List<String> roleNames = loginMapper.getRoleNames(userName);
        return roleNames;
    }

    /**
     * 根据账号获取所拥有的菜单
     *
     * @param name
     * @return
     */
    @Override
    public List<String> getAllMenusByAccount(String name, Integer firstMenuId) {
        //用于收集得到的所有菜单id
        List<Integer> menuIds = new ArrayList<>();

        Menu menu = new Menu();
        menu.setParentId(firstMenuId);
        //获得搜索到的所有的二级菜单的id
        List<Integer> ids = menuMapper.select(menu)
                .stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
        menuIds.addAll(ids);//放入二级菜单们
        for (Integer id : ids) {
            Menu menu1 = new Menu();
            menu1.setParentId(id);
            //获得搜索到的所有的三级菜单的id
            List<Integer> idss = menuMapper.select(menu1)
                    .stream()
                    .map(Menu::getId)
                    .collect(Collectors.toList());
            menuIds.addAll(idss);//放入三级菜单们
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("menuIds", menuIds);
        List<String> allMenusByName = loginMapper.getAllMenusByName(map);
        return allMenusByName;
    }
}
