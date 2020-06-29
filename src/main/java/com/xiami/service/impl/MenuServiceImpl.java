package com.xiami.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.MenuMapper;
import com.xiami.entity.Menu;
import com.xiami.entity.User;
import com.xiami.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­06­02 23:13
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public ResponseResult getMenuJsonList() {
        List<Menu> menus = menuMapper.selectAll();
        List<Menu> supers = menus.stream()
                .filter(menu -> menu.getParentId()==0)
                .collect(Collectors.toList());
        menus.removeAll(supers);
        supers.sort(Comparator.comparingInt(Menu::getOrderNum));
        JSONArray jsonArr = new JSONArray();
        for (Menu sysMenu : supers) {
            Menu child = child(sysMenu, menus, 0, 0);
            jsonArr.add(child);
        }
        //JSONArray转list
        List<Menu> lists = JSONObject.parseArray(jsonArr.toString(),Menu.class);
        PageInfo<Menu> info = new PageInfo<Menu>(lists);
        long total = info.getTotal();
        PageResult pageResult = new PageResult(total, lists);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取菜单列表数据成功", pageResult);
    }

    public Menu child(Menu menu, List<Menu> menus, Integer pNum, Integer num) {
        List<Menu> childSysMenu = menus.stream()
                .filter(s -> s.getParentId().equals(menu.getId()))
                .collect(Collectors.toList());
        menus.removeAll(childSysMenu);
        Menu m;
        for (Menu menu1 : childSysMenu) {
            ++num;
            m = child(menu1, menus, pNum, num);
            menu.addChild(menu1);
        }
        return menu;
    }
}

