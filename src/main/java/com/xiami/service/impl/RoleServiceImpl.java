package com.xiami.service.impl;

import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.entity.Role;
import com.xiami.entity.User;
import com.xiami.service.RoleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xiami.dao.RoleMapper;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;


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
}
