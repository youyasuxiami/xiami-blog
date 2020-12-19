package com.xiami.realm;

import com.xiami.JWTUtil;
import com.xiami.dao.MenuMapper;
import com.xiami.dao.RoleMapper;
import com.xiami.entity.Menu;
import com.xiami.entity.Role;
import com.xiami.entity.User;
import com.xiami.filter.JWTToken;
import com.xiami.jwt.BaseJwtInfo;
import com.xiami.service.MenuService;
import com.xiami.service.ProfileService;
import com.xiami.service.RoleService;
import com.xiami.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;


    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //String username = usernamePasswordToken.getUsername();
        //String password = new String(usernamePasswordToken.getPassword());
        //User user = profileService.getUserInfo(username);
        //if (user == null) {
        //    throw new UnknownAccountException("账户不存在");
        //}
        //if (!user.getPassword().equals(password)) {
        //    throw new IncorrectCredentialsException("密码不正确");
        //}
        //if (user.getStatus().equals("0")) {
        //    throw new LockedAccountException("账户被冻结，请联系管理员");
        //}
        ////第一个参数用户对象，第二个是前端传过来的密码，第三个是shiro任意名
        //SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, this.getName());
        //return info;
        //获得token
        String token = (String) authenticationToken.getCredentials();
        //从token中获得username
        String username = JWTUtil.getUsername(token);
        //如果username为空或者验证不匹配
        if(username == null||!JWTUtil.verify(token,username)){
            throw new AuthenticationException("token认证失败!");
        }
        User user = userService.getUserByName(username);
        BaseJwtInfo baseJwtInfo=new BaseJwtInfo();
        baseJwtInfo.setName(user.getName());
        baseJwtInfo.setUserId(user.getId());

        String password=user.getPassword();
        //如果没有查询到用户名对应的密码
        if(password==null){
            throw new AuthenticationException("该用户不存在");
        }
        return new SimpleAuthenticationInfo(baseJwtInfo,token,"UserRealm");
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前用户信息
        //User user = (User) principalCollection.getPrimaryPrincipal();
        ////获取当前用户的编号
        //Integer userId = user.getId();
        //
        ////获取该用户编号的所有角色
        //List<String> roleNames = roleMapper.getRoleNames(userId);
        ////获取该用户编号的所有的菜单权限
        //List<String> menuPerms = menuMapper.getMenuPerms(userId);
        //
        ////添加认证信息
        //SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addRoles(roleNames);
        //info.addStringPermissions(menuPerms);
        //
        //return info;

        String username = JWTUtil.getUsername(principalCollection.toString());
        //获得该用户名的所有角色
        List<Role> roleList = roleService.getAllRolesByName(username);
        Set<String>roleSet =new HashSet<>();
        for (Role role : roleList) {
            roleSet.add(role.getRoleName());
        }
        //获得该用户名的所有权限
        List<Menu> menuList = menuService.getAllMenusByName(username);
        Set<String> permissionSet = new HashSet<>();
        for (Menu menu : menuList) {
            permissionSet.add(menu.getPerms());
        }
        //添加认证信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;
    }

    /**
     * 必须重写此方法，否则会报错
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof JWTToken;
    }
}
