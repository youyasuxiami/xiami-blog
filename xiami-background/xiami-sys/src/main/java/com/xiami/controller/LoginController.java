package com.xiami.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.common.collect.Maps;
import com.xiami.AccountSecurityUtils;
import com.xiami.JWTUtil;
import com.xiami.annotation.OperatorLog;
import com.xiami.base.ResponseResult;
import com.xiami.dto.LoginInfo;
import com.xiami.entity.User;
import com.xiami.filter.JWTToken;
import com.xiami.service.LoginService;
import com.xiami.utils.ShiroUtils;
import com.xiami.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­03­28 12:45
 */
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {
    @Autowired
    private DefaultKaptcha kaptcha;

    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/info")
    public ResponseResult<LoginInfo> info(Integer firstMenuId) {
        //获取用户名
        User user = UserUtils.getUser();

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setName(user.getName());
        loginInfo.setAvatar(user.getAvatar());

        List<String> roleNames = loginService.getRoleNames(user.getName());
        String[] arrs = new String[roleNames.size()];
        String[] objects = roleNames.toArray(arrs);
        loginInfo.setRoles(objects);

        if (StringUtils.isEmpty(firstMenuId)) {
            firstMenuId = 101;
        }
        List<String> urlNames = loginService.getAllMenusByAccount(user.getName(), firstMenuId);
        String[] arrs2 = new String[urlNames.size()];
        String[] objects2 = urlNames.toArray(arrs2);
        loginInfo.setUrls(objects2);
        // TODO: 2020/7/13
        loginInfo.setDesc("i am a admin");

        return new ResponseResult<LoginInfo>(ResponseResult.CodeStatus.OK, "获取用户信息", loginInfo);
    }

    /**
     * 注销
     *
     * @return {@link ResponseResult}
     */
    @OperatorLog("后台注销")
    @PostMapping(value = "/logout")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        // 获取 token
        //String token = request.getParameter("access_token");
        // 删除 token 以注销
        //OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        //tokenStore.removeAccessToken(oAuth2AccessToken);
        return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "用户已注销");
    }

    /**
     * 输出验证码图片
     *
     * @param response
     */
    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response) {
        // 缓存设置-设置不缓存（可选操作）
        response.setHeader("Cache-Control", "no-store, no-cache");
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
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OperatorLog("后台登录")
    @PostMapping(value = "/login")
    public ResponseResult<Map<String, Object>> login(String username,String password,String captcha) {
        //比对验证码
        String serverKaptcha = ShiroUtils.getKaptcha();
        //if (!serverKaptcha.equalsIgnoreCase(captcha)) {
        //    return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.CODE_ERROR,"验证码错误" );
        //}
        Subject subject = SecurityUtils.getSubject();
        //解密
        String passwordJieMi = AccountSecurityUtils.decrypt(password);
        String passwordJiaMi = new Md5Hash(passwordJieMi, username, 1024).toBase64();
        //加密（数据库的密码）
        //String passwordJiaMi = MD5Utils.md5(passwordJieMi, loginParam.getUsername(), 1024);
        //通过subject 身份认证
        //UsernamePasswordToken token = new UsernamePasswordToken(loginParam.getUsername(), passwordJiaMi);
        //if (userDTO.isRememberMe()) {
        //    token.setRememberMe(true);
        //}

        //储存,生成token
        Map<String, String> map = new HashMap<>();
        map.put("name", username);
        String token = JWTUtil.createToken(map,passwordJiaMi);
        JWTToken jwtToken = new JWTToken(token);
        //try {
            subject.login(jwtToken);
            Map<String, Object> result = Maps.newHashMap();
            result.put("token", token);
            return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.OK, "登录成功", result);
        //}
        //catch (AuthenticationException e) {
        //    log.error("登录失败:{}",e);
        //    return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.ERROR_ACCOUNT_PASSWORD, "用户名或者密码错误，请重新输入");
        //}
    }

    /**
     * 返回公钥给前端
     *
     * @return
     */
    @GetMapping("/getPublicKey")
    public ResponseResult getPublicKey() {
        return new ResponseResult(ResponseResult.CodeStatus.OK, "登录成功", AccountSecurityUtils.PUBLIC_KEY);
    }
}
