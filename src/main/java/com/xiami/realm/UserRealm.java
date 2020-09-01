package com.xiami.realm;

import com.xiami.dao.MenuMapper;
import com.xiami.dao.RoleMapper;
import com.xiami.entity.User;
import com.xiami.service.ProfileService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author：郑锦
 * Date：2020­06­01 22:57
 * Description：<描述>
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        User user = profileService.getUserInfo(username);
        if (user == null) {
            throw new UnknownAccountException("账户不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new IncorrectCredentialsException("密码不正确");
        }
        if (user.getStatus().equals("0")) {
            throw new LockedAccountException("账户被冻结，请联系管理员");
        }
        //第一个参数用户对象，第二个是前端传过来的密码，第三个是shiro任意名
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, this.getName());
        return info;
    }

    /**
     * 认证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前用户信息
        User user = (User) principalCollection.getPrimaryPrincipal();
        //获取当前用户的编号
        Integer userId = user.getId();

        //获取该用户编号的所有角色
        List<String> roleNames = roleMapper.getRoleNames(userId);
        //获取该用户编号的所有的菜单权限
        List<String> menuPerms = menuMapper.getMenuPerms(userId);

        //添加认证信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roleNames);
        info.addStringPermissions(menuPerms);

        return info;
    }
}
