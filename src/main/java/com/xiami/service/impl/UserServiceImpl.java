package com.xiami.service.impl;

import com.xiami.dao.UserMapper;
import com.xiami.entity.User;
import com.xiami.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­24 17:10
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名：获取用户信息
     * @param name
     * @return
     */
    public User get(String name){
        Example example=new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name",name);
        return userMapper.selectOneByExample(example);
    }

    public User getUserInfo(String username){
        User user=get(username);
        return user;
    }

    @Override
    public int updateUserInfo(User user) {
        User user1=get(user.getName());

        user1.setNickName(user.getNickName());
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
    public List<User> getUsers(){
        return userMapper.selectAll();
    }
}
