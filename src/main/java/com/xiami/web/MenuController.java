package com.xiami.web;

import com.alibaba.fastjson.JSONArray;
import com.xiami.base.ResponseResult;
import com.xiami.entity.Menu;
import com.xiami.entity.User;
import com.xiami.service.MenuService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­30 17:19
 */
@RequestMapping("/menu")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping(value = "/menuList")
    public ResponseResult menuList(String pageNum,String pageSize){
            return menuService.getMenuJsonList();
    }

    /**
     * 新增/编辑用户
     *
     * @param menu
     * @return
     */
    @PostMapping("/addMenu")
    public ResponseResult<User> addUser(@RequestBody Menu menu) {
        //新增用户
        if (menu.getId() == null) {
            if("0".equals(menu.getType()+"")){
                menu.setParentId(0);
            }
            menu.setCreateTime(new Date());
            menu.setUpdateTime(new Date());
            return menuService.addMenu(menu);
        }

        //编辑用户
        menu.setUpdateTime(new Date());
        return menuService.updateMenu(menu);
    }

}
