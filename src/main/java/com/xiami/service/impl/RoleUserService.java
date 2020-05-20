package com.xiami.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xiami.dao.RoleUserMapper;
@Service
public class RoleUserService{

    @Resource
    private RoleUserMapper roleUserMapper;

}
