package com.xiami.service.impl;

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
}
