package com.xiami.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xiami.dao.PermissionMapper;
@Service
public class PermissionService{

    @Resource
    private PermissionMapper permissionMapper;

}
