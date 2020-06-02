package com.xiami.web;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.common.collect.Maps;
import com.xiami.base.ResponseResult;
import com.xiami.base.ResponseResult1;
import com.xiami.dto.LoginInfo;
import com.xiami.dto.LoginParam;
import com.xiami.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    @Autowired
    private DefaultKaptcha kaptcha;

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

    /**
     * 输出验证码图片
     * @param response
     */
    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response){
        // 缓存设置-设置不缓存（可选操作）
        response.setHeader("Cache-Control","no-store, no-cache");
        // 设置响应内容
        response.setContentType("image/jpg");
        //生成验证码
        String text = kaptcha.createText();//文本
        //生成图片
        BufferedImage image = kaptcha.createImage(text);
        //验证码存储到shiro的 session
        ShiroUtils.setKaptcha(text);
        try {
            //返回到页面
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"jpg",outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
