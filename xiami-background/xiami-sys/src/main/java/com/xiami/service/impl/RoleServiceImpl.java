package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.BeanUtil;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.RoleMapper;
import com.xiami.dao.RolePermissionMapper;
import com.xiami.dao.RoleUserMapper;
import com.xiami.dto.RoleParam;
import com.xiami.dto.RoleQueryDto;
import com.xiami.entity.Role;
import com.xiami.entity.RolePermission;
import com.xiami.entity.RoleUser;
import com.xiami.service.RoleService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private RoleUserMapper roleUserMapper;

    @Override
    public List<String> getRoleNames(Integer userId) {
        List<String> roleNames = roleMapper.getRoleNames(userId);
        return roleNames;
    }

    @Override
    public ResponseResult getRoleList(RoleQueryDto roleQueryDto) {
        Example example = new Example(Role.class);//实体类
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(roleQueryDto.getRoleName())) {//模糊查询
            criteria.andLike("roleName", "%" + roleQueryDto.getRoleName() + "%");//实体类属性
        }
        if (!StringUtils.isEmpty(roleQueryDto.getRoleDesc())) {
            criteria.andLike("roleDesc", "%" + roleQueryDto.getRoleDesc() + "%");//实体类属性
        }
        //使用PageHelper.startPage只是针对接下来的一条查询语句
        PageHelper.startPage(roleQueryDto.getPageNum(), roleQueryDto.getPageSize());
        //根据条件查询
        List<Role> roles = roleMapper.selectByExample(example);
        PageInfo<Role> info = new PageInfo<>(roles);
        long total = info.getTotal();
        PageResult pageResult = new PageResult(total, roles);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取角色列表数据成功", pageResult);
    }

    @Transactional
    @Override
    public ResponseResult addRole(RoleParam param) {
        Role role = new Role();
        role.setRoleName(param.getRoleName());
        role.setRoleDesc(param.getRoleDesc());
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        //添加数据到role表，返回角色Id
        try {
            int i = roleMapper.insertUseGeneratedKeys(role);
            if (param.getMenusSelect().length == 0) {
                return new ResponseResult<>(ResponseResult.CodeStatus.OK, "添加角色成功");
            }
        } catch (DuplicateKeyException e) {
            String[] code1 = BeanUtil.getCode(e);
            String code = code1[1];
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "数据库中已经存在用户名为 " + code + " 的数据，请重新输入用户名");
        }
        //添加数据到role_menu表
        List<String> menuList = Arrays.asList(param.getMenusSelect());
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (String menuId : menuList) {
            RolePermission permission = new RolePermission();
            permission.setRoleId(role.getId());
            permission.setPermissionId(Integer.valueOf(menuId));
            permission.setCreateTime(new Date());
            permission.setUpdateTime(new Date());
            rolePermissions.add(permission);
        }
        int i = 0;
        try {
            i = rolePermissionMapper.insertRolePermission(rolePermissions);
        } catch (DuplicateKeyException e) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "数据库中已经存在该角色的数据，请重新添加角色");
        }
        if (i > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "添加角色成功");
        } else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "添加角色失败");
        }
    }

    @Override
    public ResponseResult getMenusByRoleId(Integer id) {
        Example example = new Example(RolePermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", id);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(example);
        List<Integer> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取角色所拥有的菜单成功", permissionIds);
    }

    @Transactional
    @Override
    public ResponseResult updateRole(RoleParam param) {
        Role role = new Role();
        role.setRoleName(param.getRoleName());
        role.setRoleDesc(param.getRoleDesc());
        role.setUpdateTime(new Date());
        //添加数据到role表，返回角色Id
        try {
            int updateNum = roleMapper.updateByPrimaryKey(role);
        } catch (DuplicateKeyException e) {
            String[] code1 = BeanUtil.getCode(e);
            String code = code1[1];
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "数据库中已经存在用户名为 " + code + " 的数据，请重新编辑用户名");
        }

        //勾选了菜单
        if (null != param.getMenusSelect() && param.getMenusSelect().length != 0) {
            //1、先删除所有菜单
            //获取原菜单，如果原来是空菜单，不需要删除
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(param.getId());
            List<RolePermission> select = rolePermissionMapper.select(rolePermission);

            if (null == select && select.isEmpty()) {
                return new ResponseResult<>(ResponseResult.CodeStatus.OK, "编辑角色成功");
            } else {
                int deleteNum = rolePermissionMapper.delete(rolePermission);
                //2、再获取选中菜单，从而添加菜单
                List<String> menuList = Arrays.asList(param.getMenusSelect());
                List<RolePermission> rolePermissions = new ArrayList<>();
                for (String menuId : menuList) {
                    RolePermission permission = new RolePermission();
                    permission.setRoleId(param.getId());
                    permission.setPermissionId(Integer.valueOf(menuId));
                    rolePermissions.add(permission);
                }
                try {
                    int i = rolePermissionMapper.insertRolePermission(rolePermissions);
                    if (i > 0) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "编辑角色成功");
                    } else {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "编辑角色失败1");
                    }
                } catch (DuplicateKeyException e) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "数据库中已经存在该角色的数据，请重新编辑该角色");
                }
            }
        } else {//没有勾选菜单，如果原来是空菜单，那么不需要做操作，如果原来不是空菜单，那么要清空
            //要删除的RolePermission
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(param.getId());
            List<RolePermission> select = rolePermissionMapper.select(rolePermission);
            if (null == select || select.isEmpty()) {
                return new ResponseResult<>(ResponseResult.CodeStatus.OK, "编辑角色成功");
            } else {
                int deleteNum = rolePermissionMapper.delete(rolePermission);
                if (deleteNum > 0) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.OK, "编辑角色成功");
                } else {
                    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "编辑角色失败2");
                }
            }
        }
    }

    @Transactional
    @Override
    public ResponseResult deleteRole(Integer id) {
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleId(id);
        List<RoleUser> select = roleUserMapper.select(roleUser);
        if (null != select && select.size() != 0) {//有绑定用户，不能删除
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "选中的角色已经和用户绑定，请先在用户管理中解绑菜单");
        }
        //获取中间表中是否存在该角色的菜单
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(id);

        List<RolePermission> rolePermissionList = null;
        try {
            rolePermissionList = rolePermissionMapper.select(rolePermission);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rolePermissionList != null && rolePermissionList.size() != 0) {//已有选中的菜单数据
            //1、根据角色id获取已经选中的菜单数据
            List<Integer> ids = rolePermissionList.stream()
                    .map(RolePermission::getId)
                    .collect(Collectors.toList());
            //Integer[] ids = (Integer[]) list.stream().toArray();
            try {
                //2、先删除一个角色
                int i = roleMapper.deleteByPrimaryKey(id);
                //3、删除角色所选中的菜单
                int i1 = rolePermissionMapper.deleteByIds(ids);
                if (i > 0 && i1 > 0) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.OK, "删除角色成功");
                } else {
                    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "删除角色失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "删除角色失败");
            }
        } else {//中间表没有选中的菜单数据，只要删除role表的一个角色
            try {
                int i = roleMapper.deleteByPrimaryKey(id);
                if (i > 0) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.OK, "删除角色成功");
                } else {
                    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "删除角色失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "删除角色失败");
            }
        }
    }

    /**
     * 根据用户名，获得全部的角色
     * @param name
     * @return
     */
    @Override
    public List<Role> getAllRolesByName(String name) {
        List<Role> roles = roleMapper.getAllRolesByName(name);
        return roles;
    }
}
