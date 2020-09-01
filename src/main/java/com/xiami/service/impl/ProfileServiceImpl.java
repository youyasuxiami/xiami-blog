package com.xiami.service.impl;

import com.xiami.base.ResponseResult;
import com.xiami.dao.MenuMapper;
import com.xiami.dao.UserMapper;
import com.xiami.entity.Menu;
import com.xiami.entity.User;
import com.xiami.service.ProfileService;
import com.xiami.utils.AccountSecurityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 根据用户名：获取用户信息
     *
     * @param name
     * @return
     */
    public User get(String name) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", name);
        User user = userMapper.selectOneByExample(example);
        return user;
    }

    public User getUserInfo(String username) {
        User user = get(username);
        return user;
    }

    @Override
    public int updateUserInfo(User user) {
        User user1 = get(user.getName());
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
    public int modifyIcon(String username, String path) {
        User user1 = get(username);
        user1.setAvatar(path);
        return userMapper.updateByPrimaryKey(user1);
    }

    @Override
    public ResponseResult getFirstMenus() {
        //获取登录用户的信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String name = user.getName();

        //先获取该用户的所有的一级菜单
        List<Menu> allMenusByName = menuMapper.getAllMenusByName(name);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取该用户的一级菜单成功", allMenusByName);
    }

    @Override
    public ResponseResult modifyPassword(String oldPassword, String newPassword) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        User user0 = new User();
        user0.setName(user.getName());
        User user1 = userMapper.selectOne(user0);
        if (null == user1) {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "该系统不存在该用户，请联系管理员");
        }
        if (StringUtils.isEmpty(oldPassword)) {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "旧密码不能为空，请重新输入");
        }
        //旧密码解密
        String oldPassworddJieMi = AccountSecurityUtils.decrypt(oldPassword.trim());
        //旧密码加密
        String oldPasswordJiaMi = new Md5Hash(oldPassworddJieMi, user.getName(), 1024).toBase64();
        if (!oldPasswordJiaMi.equals(user1.getPassword())) {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "旧密码错误，请重新输入");
        }
        //新密码解密
        String newPasswordJieMi = AccountSecurityUtils.decrypt(newPassword.trim());
        //新密码加密
        String newPasswordJiaMi = new Md5Hash(newPasswordJieMi, user.getName(), 1024).toBase64();
        user1.setPassword(newPasswordJiaMi);
        int i = 0;
        try {
            i = userMapper.updateByPrimaryKey(user1);
            if (i > 0) {
                return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "修改密码成功");
            }
            // 失败
            else {
                return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "修改密码失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "修改密码失败");
        }
    }
}
