package com.xiami.controller;

import com.github.pagehelper.PageInfo;
import com.xiami.ImprotExcelUtil;
import com.xiami.annotation.OperatorLog;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dto.PageRequestDto;
import com.xiami.dto.UserDto;
import com.xiami.dto.UserQueryDto;
import com.xiami.entity.User;
import com.xiami.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Author：郑锦
 * Date：2020­06­10 22:24
 * Description：<描述>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 获取用户列表
     *
     * @return
     */
    @GetMapping(value = "/list")
    public PageResult getUsers(@RequestBody PageRequestDto pageRequestDto) {
        List<User> lists = userService.getUsersByPage(pageRequestDto);
        //PageInfo pageInfo=new PageInfo(userList);
        //long total = pageInfo.getTotal();
        return new PageResult(new PageInfo(lists).getTotal(), lists);
    }


    /**
     * 获取用户列表：方式二
     *
     * @return
     */
    @GetMapping(value = "/list1")
    public PageResult getUsers1(@RequestBody PageRequestDto pageRequestDto) {
        return userService.getUsersByPage1(pageRequestDto);
    }

    /**
     * 获取用户列表：根据单表的条件
     *
     * @return
     */
    @GetMapping(value = "/searchlist1")
    public PageResult getSearch1Users(@RequestBody UserQueryDto userQueryDto) {
        return userService.getUsersBySearch(userQueryDto);
    }

    /**
     * 获取用户列表：根据多表的条件
     *
     * @return
     */
    @GetMapping(value = "/searchList")
    //public ResponseResult getSearchUsers(UserQueryDto userQueryDto) {//表单json字符串请求
    public ResponseResult getSearchUsers(UserQueryDto userQueryDto) {//json字符串请求
        PageResult usersBySearch = userService.getUsersBySearch(userQueryDto);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取列表数据成功", usersBySearch);
    }

    /**
     * 新增/编辑用户
     *
     * @param userDto
     * @return
     */
    @OperatorLog("新增/编辑用户")
    @PostMapping("/addUser")
    public ResponseResult<User> addUser(@RequestBody UserDto userDto) {
        //新增用户
        if (userDto.getId() == null) {
            //初始密码（生成密码规则）
            String newPass = new Md5Hash(userDto.getPassword(), userDto.getName(), 1024).toBase64();
            userDto.setPassword(newPass);
            userDto.setAvatar(userDto.getAvatar());
            userDto.setCreateTime(new Date());
            userDto.setUpdateTime(new Date());
            userDto.setLoginTime(new Date());
            return userService.addUser(userDto);
        }

        //编辑用户
        userDto.setUpdateTime(new Date());
        return userService.updateUser(userDto);
    }

    /**
     * 禁用0、启用1
     *
     * @param id
     * @param status
     * @return
     */
    @OperatorLog("更新用户状态")
    @PostMapping("/updateUserStatus")
    public ResponseResult<User> updateUserStatus(Integer id, String status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        return userService.updateUserStatus(user);
    }

    /**
     * 删除一个User
     *
     * @param id
     * @return
     */
    @OperatorLog("删除用户")
    @PostMapping("/deleteUser")
    public ResponseResult<User> deleteUser(Integer id) {
        return userService.deleteUser(id);
    }

    /**
     * 批量导入用户
     *
     * @param file
     * @return
     */
    @OperatorLog("批量导入用户")
    @PostMapping("/importExcel")
    public ResponseResult<User> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            //对文件的全名进行截取然后在后缀名进行删选。
            int begin = file.getOriginalFilename().indexOf(".");
            int last = file.getOriginalFilename().length();
            //获得文件后缀名
            String a = file.getOriginalFilename().substring(begin, last);
            //需要的xlsx文件
            if (!a.endsWith(".xlsx")) {
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "文件格式错误");
            }
        }
        //获得excel中的数据
        List<List<Object>> dataList = ImprotExcelUtil.analysisExcel(file.getInputStream(), file.getOriginalFilename());
        //将数据插入数据库
        ResponseResult responseResult = userService.importExcel(dataList);
        return responseResult;
    }

    /**
     * 导出用户数据
     *
     * @param response
     * @param userQueryDto
     * @return
     * @throws IOException
     */
    @OperatorLog("批量导出用户")
    @GetMapping(value = "/exportUserToExcel")
    public void exportUserToExcel(HttpServletResponse response, UserQueryDto userQueryDto) throws Exception {

        //PageData pd = this.getPageData(page, limit);
        String fileName = URLEncoder.encode("用户表" + ".xlsx", "UTF-8");
        String headStr = "attachment; filename=\"" + fileName + "\"";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", headStr);
        OutputStream out = response.getOutputStream();
        //userService.exportUserToExcel(out, userQueryDto);
        //log.error("导出用户数据异常！", e);
        userService.exportUserToExcel(out, userQueryDto);
    }

    /**
     * 导出用户数据
     *
     * @param response
     * @param userQueryDto
     * @return
     * @throws IOException
     */
    @OperatorLog("导出用户数据")
    @GetMapping(value = "/exportAllUserToExcel")
    public void exportAllUserToExcel(HttpServletResponse response, UserQueryDto userQueryDto) throws Exception {

        //PageData pd = this.getPageData(page, limit);
        String fileName = URLEncoder.encode("用户表" + ".xlsx", "UTF-8");
        String headStr = "attachment; filename=\"" + fileName + "\"";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", headStr);
        OutputStream out = response.getOutputStream();
        //userService.exportUserToExcel(out, userQueryDto);
        //log.error("导出用户数据异常！", e);
        userService.exportAllUserToExcel(out, userQueryDto);
    }


    /**
     * 批量删除User
     *
     * @param ids
     * @return
     */
    @OperatorLog("批量删除用户")
    @GetMapping("/deleteUsers")
    public ResponseResult<User> deleteUsers(Integer[] ids) {
        return userService.deleteUsers(ids);
    }

    /**
     * 获得所有角色
     *
     * @return
     */
    @GetMapping("/getRoles")
    public ResponseResult<User> getRoles() {
        return userService.getRoles();
    }

    /**
     * 获得一个用户所拥有的角色
     *
     * @return
     */
    @GetMapping("/getCheckedRoles")
    public ResponseResult<User> getCheckedRoles(Integer id) {
        return userService.getCheckedRoles(id);
    }

    /**
     * 重置密码
     * @param id
     * @return
     */
    @OperatorLog("重置密码")
    @PostMapping("/resetUser")
    public ResponseResult<User> resetUser(Integer id) {
        int i = userService.resetUser(id);
        if(i>0){
            return new ResponseResult<>(ResponseResult.CodeStatus.OK,"重置密码成功,新密码是123456");
        }else{
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL,"重置密码失败");
        }
    }
}
