package com.xiami.web;

import com.xiami.base.ResponseResult;
import com.xiami.service.MenuService;
import com.xiami.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­30 17:19
 */
@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/roleList")
    public ResponseResult menuList(String pageNum, String pageSize) {
        return roleService.getRoleList();
    }
}
