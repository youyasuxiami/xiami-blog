package com.xiami.web;

import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dto.PageRequestDto;
import com.xiami.dto.UserQueryDto;
import com.xiami.entity.User;
import com.xiami.service.SysService;
import com.xiami.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Author：郑锦
 * Date：2020­06­10 22:24
 * Description：<描述>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping(value = "/list")
    public PageResult getUsers(@RequestBody PageRequestDto pageRequestDto) {
        List<User> lists = userService.getUsersByPage(pageRequestDto);
        //PageInfo pageInfo=new PageInfo(userList);
        //long total = pageInfo.getTotal();
        return new PageResult(new PageInfo(lists).getTotal(),lists);
    }


    /**
     * 获取用户列表：方式二
     * @return
     */
    @GetMapping(value = "/list1")
    public PageResult getUsers1(@RequestBody PageRequestDto pageRequestDto) {
        return userService.getUsersByPage1(pageRequestDto);
    }

    /**
     * 获取用户列表：根据单表的条件
     * @return
     */
    @GetMapping(value = "/searchlist1")
    public PageResult getSearch1Users(@RequestBody UserQueryDto userQueryDto) {
        return userService.getUsersBySearch(userQueryDto);
    }

    /**
     * 获取用户列表：根据多表的条件
     * @return
     */
    @GetMapping(value = "/searchList")
    //public ResponseResult getSearchUsers(UserQueryDto userQueryDto) {//表单json字符串请求
    public ResponseResult getSearchUsers(UserQueryDto userQueryDto) {//json字符串请求
        PageResult usersBySearch = userService.getUsersBySearch(userQueryDto);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取列表数据成功",usersBySearch);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("/addUser")
    public ResponseResult<User> addUser(@RequestBody User user){
        //新增用户
        if(user.getId()==null){
            //初始密码
            String newPass = new Md5Hash("123456", user.getName(), 1024).toBase64();
            user.setPassword(newPass);
            user.setAvatar("http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setLoginTime(new Date());
            return userService.addUser(user);
        }

        //编辑用户
        user.setUpdateTime(new Date());
        return userService.updateUser(user);
    }
}
