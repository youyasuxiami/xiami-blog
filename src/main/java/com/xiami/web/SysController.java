package com.xiami.web;

import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dto.PageRequestDto;
import com.xiami.dto.UserQueryDto;
import com.xiami.entity.User;
import com.xiami.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­30 17:19
 */
@RequestMapping("/sys")
@RestController
public class SysController {

    @Autowired
    private SysService sysService;

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping(value = "/user/list")
    public PageResult getUsers(@RequestBody PageRequestDto pageRequestDto) {
        List<User> lists = sysService.getUsersByPage(pageRequestDto);
        //PageInfo pageInfo=new PageInfo(userList);
        //long total = pageInfo.getTotal();
        return new PageResult(new PageInfo(lists).getTotal(),lists);
    }


    /**
     * 获取用户列表：方式二
     * @return
     */
    @GetMapping(value = "/user/list1")
    public PageResult getUsers1(@RequestBody PageRequestDto pageRequestDto) {
        return sysService.getUsersByPage1(pageRequestDto);
    }

    /**
     * 获取用户列表：根据单表的条件
     * @return
     */
    @GetMapping(value = "/user/searchlist1")
    public PageResult getSearch1Users(@RequestBody UserQueryDto userQueryDto) {
        return sysService.getUsersBySearch(userQueryDto);
    }

    /**
     * 获取用户列表：根据多表的条件
     * @return
     */
    @PostMapping(value = "/user/searchlist")
    //public ResponseResult getSearchUsers(UserQueryDto userQueryDto) {//表单json字符串请求
    public ResponseResult getSearchUsers(@RequestBody UserQueryDto userQueryDto) {//json字符串请求
        PageResult usersBySearch = sysService.getUsersBySearch(userQueryDto);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取列表数据成功",usersBySearch);
    }
}
