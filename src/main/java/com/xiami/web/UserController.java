package com.xiami.web;

import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dto.PageRequestDto;
import com.xiami.dto.UserQueryDto;
import com.xiami.entity.User;
import com.xiami.service.SysService;
import com.xiami.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @PostMapping(value = "/searchlist")
    //public ResponseResult getSearchUsers(UserQueryDto userQueryDto) {//表单json字符串请求
    public ResponseResult getSearchUsers(@RequestBody UserQueryDto userQueryDto) {//json字符串请求
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
        int i = userService.addUser(user);
        if(i>0){
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"新增数据成功");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL,"新增数据失败");
    }
}
