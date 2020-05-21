package com.xiami.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xiami.dao.UserMapper;
@Service
public class UserService{

    @Resource
    private UserMapper userMapper;

}
