package com.xiami.service.impl;

import com.xiami.dao.LoginMapper;
import com.xiami.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public List<String> getRoleNames(String userName) {
        List<String> roleNames = loginMapper.getRoleNames(userName);
        return roleNames;
    }
}
