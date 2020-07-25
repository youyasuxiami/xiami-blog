package com.xiami.service;

import com.xiami.base.ResponseResult;
import com.xiami.entity.User;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­24 17:14
 */
public interface ProfileService {
     /**
      * 获得用户信息
      * @param username
      * @return
      */
     User getUserInfo(String username);

     /**
      * 更新用户信息
      * @param user
      * @return
      */
     int updateUserInfo(User user);

     /**
      * 修改头像
      * @param username
      * @param path
      * @return
      */
     int modifyIcon(String username,String path);

     /**
      * 获取该用户所有的一级菜单
      * @return
      */
     ResponseResult getFirstMenus();

     /**
      * 修改密码
      * @param oldPassword
      * @param newPassword
      * @return
      */
     ResponseResult  modifyPassword(String oldPassword,String newPassword );
}
