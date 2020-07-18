package com.xiami.service.impl;

import com.xiami.base.ResponseResult;
import com.xiami.dao.MenuMapper;
import com.xiami.dao.UserMapper;
import com.xiami.entity.User;
import com.xiami.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­24 17:10
 */
@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 根据用户名：获取用户信息
     * @param name
     * @return
     */
    public User get(String name){
        Example example=new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name",name);
        User user = userMapper.selectOneByExample(example);
        return user;
    }

    public User getUserInfo(String username){
        User user=get(username);
        return user;
    }

    @Override
    public int updateUserInfo(User user) {
        User user1=get(user.getName());
        user1.setNickName(user.getNickName());
        user1.setAvatar(user.getAvatar());
        user1.setSex(user.getSex());
        user1.setAge(user.getAge());
        user1.setPhone(user.getPhone());
        user1.setEmail(user.getEmail());
        user1.setPs(user.getPs());
        user1.setStatus(user.getStatus());
        return userMapper.updateByPrimaryKey(user1);
    }

    @Override
    public int modifyIcon(String username,String path){
        User user1=get(username);
        user1.setAvatar(path);
        return userMapper.updateByPrimaryKey(user1);
    }

    @Override
    public ResponseResult getFirstMenus() {
        // TODO: 2020/7/18 先获取该用户的所有角色
        return null;
    }
}
