package com.xiami.service.impl;

import com.xiami.dao.SysOperLogMapper;
import com.xiami.dao.UserMapper;
import com.xiami.service.BackGroundHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2021­02­02 23:02
 */
@Service
public class BackGroundHomeServiceImpl implements BackGroundHomeService {
    @Resource
    private UserMapper userMapper;

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    @Override
    public int getAddNum() {
        int i = userMapper.selectByCurrentMonth();
        return i;
    }

    @Override
    public int getOnlineNum() {
        int i = sysOperLogMapper.selectByCurrentDay();
        return i;
    }
}
