package com.xiami.service.impl;

import com.xiami.service.RoleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xiami.dao.RoleMapper;
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;


}
