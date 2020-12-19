package com.xiami.service.impl;

import com.xiami.dao.RoleUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleUserService {

    @Resource
    private RoleUserMapper roleUserMapper;

}
