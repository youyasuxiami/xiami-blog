package com.xiami.service.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.xiami.dao.RolePermissionMapper;

@Service
public class RolePermissionService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

}
