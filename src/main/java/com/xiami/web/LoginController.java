package com.xiami.web;

import com.google.common.collect.Maps;
import com.xiami.base.ResponseResult;
import com.xiami.base.ResponseResult1;
import com.xiami.dto.LoginInfo;
import com.xiami.dto.LoginParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­03­28 12:45
 */
@RestController
public class LoginController {
    @GetMapping(value="/login")
    public ResponseResult1 login(){
        return new ResponseResult1(ResponseResult1.CodeStatus.OK,"成功",null);
    }
    //@PostMapping(value = "/user/login")
    //public ResponseResult1 login( LoginParam loginParam){
    //    System.out.println("11111111111111");
    //    return new ResponseResult1(ResponseResult1.CodeStatus.OK,"成功",null);
    //}

    @PostMapping(value = "/user/login")
    public ResponseResult<Map<String,Object>> login( LoginParam loginParam){
        Map<String,Object> result= Maps.newHashMap();
        result.put("token","123456");
        return new ResponseResult<Map<String,Object>>(20000, HttpStatus.OK.toString(),result);
    }

    @GetMapping(value = "/user/info")
    public ResponseResult<LoginInfo>info(){
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginInfo loginInfo=new LoginInfo();
        //loginInfo.setName(authentication.getName());
        loginInfo.setName("zhengjin");
        loginInfo.setAvatar("https://italker-news.oss-cn-shenzhen.aliyuncs.com/xiaoxin2.jpg");
        return new ResponseResult<LoginInfo>(ResponseResult.CodeStatus.OK,"获取用户信息",loginInfo);
    }

    /**
     * 注销
     *
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "/user/logout")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        // 获取 token
        String token = request.getParameter("access_token");
        // 删除 token 以注销
        //OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        //tokenStore.removeAccessToken(oAuth2AccessToken);
        return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "用户已注销");
    }

}
