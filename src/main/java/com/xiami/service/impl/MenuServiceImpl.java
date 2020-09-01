package com.xiami.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.MenuMapper;
import com.xiami.dao.RolePermissionMapper;
import com.xiami.entity.Menu;
import com.xiami.entity.RolePermission;
import com.xiami.service.MenuService;
import org.springframework.stereotype.Service;

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

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public ResponseResult getMenuJsonList() {
        List<Menu> menus = menuMapper.selectAll();
        List<Menu> supers = menus.stream()
                .filter(menu -> menu.getParentId() == 0)
                .collect(Collectors.toList());
        menus.removeAll(supers);
        supers.sort(Comparator.comparingInt(Menu::getOrderNum));
        JSONArray jsonArr = new JSONArray();
        for (Menu sysMenu : supers) {
            //参数一:一个父菜单，参数二:全部的菜单
            Menu child = child(sysMenu, menus, 0, 0);
            jsonArr.add(child);
        }
        //JSONArray转list
        List<Menu> lists = JSONObject.parseArray(jsonArr.toString(), Menu.class);
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

    @Override
    public ResponseResult addMenu(Menu menu) {
        int i = menuMapper.insert(menu);
        if (i > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "提交成功");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "提交失败");
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        int i = menuMapper.updateMenu(menu);
        if (i > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "提交成功");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "提交失败");
    }

    @Override
    public ResponseResult deleteMenu(Integer id) {
        Menu menu = new Menu();
        menu.setParentId(id);
        //查找选中菜单的所有子菜单
        List<Menu> select = menuMapper.select(menu);
        List<Integer> menuIds = select.stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
        //加上自身的菜单id
        menuIds.add(id);

        //先查有没有用户用到这些菜单，有就不能删除
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByMenuIds(menuIds);
        if (null != rolePermissions && rolePermissions.size() != 0) {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "选中的菜单已经和角色绑定，请先在角色管理中解绑菜单");
        }
        try {
            int i = menuMapper.deleteMenu(menuIds);
            if (i > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "删除菜单成功");
            } else {
                return new ResponseResult(ResponseResult.CodeStatus.FAIL, "删除菜单失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "删除菜单失败");
        }
    }
}

