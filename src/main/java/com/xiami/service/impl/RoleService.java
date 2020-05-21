package com.xiami.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xiami.dao.RoleMapper;
@Service
public class RoleService{

    @Resource
    private RoleMapper roleMapper;

}
