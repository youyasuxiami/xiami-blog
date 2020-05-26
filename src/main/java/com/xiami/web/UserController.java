package com.xiami.web;

import com.xiami.base.ResponseResult;
import com.xiami.dto.IconParam;
import com.xiami.dto.UserParam;
import com.xiami.entity.User;
import com.xiami.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­24 17:09
 */
@RequestMapping("/api")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获得一个账号信息
     *
     * @param username
     * @return
     */
    @GetMapping("/profile/info/{username}")
    public ResponseResult<User> getUserInfo(@PathVariable String username) {
        User userInfo = userService.getUserInfo(username);
        System.out.println(userInfo);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取个人信息成功", userInfo);
    }

    /**
     * 更新账号信息
     * @param userParam
     * @return
     */
    @PostMapping("/profile/update")
    public ResponseResult<User> updateUserInfo(@RequestBody UserParam userParam) {
        User user=new User();
        BeanUtils.copyProperties(userParam,user);
        int result = userService.updateUserInfo(user);

        //成功
        if(result>0){
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新个人信息成功");
        }
        //失败
        else{
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "更新个人信息失败");
        }
    }
    /**
     * 修改头像
     *
     * @param iconParam {@link IconParam}
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "/profile/modify/icon")
    public ResponseResult<Void> modifyIcon(@RequestBody IconParam iconParam) {
        int result = userService.modifyIcon(iconParam.getUsername(), iconParam.getPath());
        // 成功
        if (result > 0) {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "更新头像成功");
        }
        // 失败
        else {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "更新头像失败");
        }
    }


}
