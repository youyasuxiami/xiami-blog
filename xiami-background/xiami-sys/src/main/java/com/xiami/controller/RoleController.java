package com.xiami.controller;

import com.xiami.annotation.OperatorLog;
import com.xiami.base.ResponseResult;
import com.xiami.dto.RoleParam;
import com.xiami.dto.RoleQueryDto;
import com.xiami.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­30 17:19
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/roleList")
    public ResponseResult menuList(RoleQueryDto roleQueryDto) {
        return roleService.getRoleList(roleQueryDto);
    }

    /**
     * 添加角色
     *
     * @param param
     * @return
     */
    @OperatorLog("添加角色")
    @PostMapping(value = "/addRole")
    public ResponseResult addRole(@RequestBody RoleParam param) {
        //新增
        if (null == param.getId()) {
            return roleService.addRole(param);
        }
        //编辑
        return roleService.updateRole(param);
    }

    /**
     * 根据角色Id获取菜单列表
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getMenusByRoleId")
    public ResponseResult getMenusByRoleId(Integer id) {
        return roleService.getMenusByRoleId(id);
    }

    /**
     * 删除一个角色
     *
     * @param id
     * @return
     */
    @OperatorLog("删除一个角色")
    @GetMapping("/deleteRole")
    public ResponseResult deleteUser(Integer id) {
        return roleService.deleteRole(id);
    }
}
