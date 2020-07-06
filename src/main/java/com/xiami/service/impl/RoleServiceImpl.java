package com.xiami.service.impl;

import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.RolePermissionMapper;
import com.xiami.dto.RoleParam;
import com.xiami.entity.Role;
import com.xiami.entity.RolePermission;
import com.xiami.entity.User;
import com.xiami.service.RoleService;
import com.xiami.utils.BeanUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xiami.dao.RoleMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;


    @Override
    public List<String> getRoleNames(Integer userId) {
        List<String> roleNames = roleMapper.getRoleNames(userId);
        return roleNames;
    }

    @Override
    public ResponseResult getRoleList() {
        List<Role> roles = roleMapper.selectAll();
        PageInfo<Role> info = new PageInfo<>(roles);
        long total = info.getTotal();
        PageResult pageResult = new PageResult(total, roles);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取角色列表数据成功",pageResult);
    }

    @Override
    public ResponseResult addRole(RoleParam param) {
        Role role=new Role();
        role.setRoleName(param.getRoleName());
        role.setRoleDesc(param.getRoleDesc());
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        //添加数据到role表，返回角色Id
        try {
            roleMapper.insertUseGeneratedKeys(role);
        } catch (DuplicateKeyException e) {
            String[] code1 = BeanUtil.getCode(e);
            String code = code1[1];
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "数据库中已经存在用户名为 " + code + " 的数据，请重新导入");
        }
        //添加数据到role_menu表
        List<String> menuList = Arrays.asList(param.getMenusSelect());
        List<RolePermission> rolePermissions=new ArrayList<>();
        for (String menuId : menuList) {
            RolePermission permission=new RolePermission();
            permission.setRoleId(role.getId());
            permission.setPermissionId(Integer.valueOf(menuId));
            permission.setCreateTime(new Date());
            permission.setUpdateTime(new Date());
            rolePermissions.add(permission);
        }
        int i = 0;
        try {
            i = rolePermissionMapper.insertRolePermission(rolePermissions);
        } catch (DuplicateKeyException e) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "数据库中已经存在该角色的数据，请重新添加角色");
        }
        if(i>0){
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "添加角色成功");
        }else{
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "添加角色失败");
        }
    }

}
