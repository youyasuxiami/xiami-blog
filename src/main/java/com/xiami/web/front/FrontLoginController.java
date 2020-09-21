package com.xiami.web.front;

import com.xiami.base.ResponseResult;
import com.xiami.dto.FrontLoginInfo;
import com.xiami.dto.FrontLoginParam;
import com.xiami.dto.UserDto;
import com.xiami.entity.User;
import com.xiami.service.UserService;
import com.xiami.utils.AccountSecurityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­09­06 15:47
 */
@RestController
@RequestMapping("/login")
public class FrontLoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserDto userDto) {
        return userService.addMember(userDto);
    }

    @PostMapping(value = "/login")
    public ResponseResult login(@RequestBody FrontLoginParam frontLoginParam) {
        Subject subject = SecurityUtils.getSubject();
        //解密
        String passwordJieMi = AccountSecurityUtils.decrypt(frontLoginParam.getPassword().trim());
        String passwordJiaMi = new Md5Hash(passwordJieMi, frontLoginParam.getName(), 1024).toBase64();
        //加密
        //String passwordJiaMi = MD5Utils.md5(passwordJieMi, loginParam.getUsername(), 1024);
        //通过subject 身份认证
        UsernamePasswordToken token = new UsernamePasswordToken(frontLoginParam.getName(), passwordJiaMi);
        //if ("1".equals(frontLoginParam.getIsRememberMe())) {
        //    token.setRememberMe(true);
        //}
        subject.login(token);

        //Map<String, Object> result = Maps.newHashMap();
        //result.put("token", token);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "登录成功", token);
    }

    @GetMapping(value = "/info")
    public ResponseResult info() {
        //获取登录用户的信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        FrontLoginInfo frontLoginInfo = new FrontLoginInfo();
        BeanUtils.copyProperties(user,frontLoginInfo);
        frontLoginInfo.setUserId(user.getId());

        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取用户信息", frontLoginInfo);
        //return new ResponseResult(ResponseResult.CodeStatus.OK, "获取用户信息");
    }

    /**
     * 注销
     *
     * @return {@link ResponseResult}
     */
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


    @GetMapping(value = "/getUserInfo")
    public ResponseResult getUserInfo(Integer blogId) {
        return userService.getUserInfo(blogId);
    }

}
