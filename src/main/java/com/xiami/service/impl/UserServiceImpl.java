package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.RoleUserMapper;
import com.xiami.dao.SysMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.PageRequestDto;
import com.xiami.dto.UserQueryDto;
import com.xiami.entity.RoleUser;
import com.xiami.entity.User;
import com.xiami.service.UserService;
import com.xiami.utils.DictionaryUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author：郑锦
 * Date：2020­06­10 22:34
 * Description：<描述>
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private SysMapper sysMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleUserMapper roleUserMapper;

    @Resource
    private DictionaryUtils dictionaryUtils;

    @Override
    public List<User> getUsersByPage(PageRequestDto pageRequestDto) {
        //使用PageHelper.startPage只是针对接下来的一条查询语句
        PageHelper.startPage(pageRequestDto.getPageNum(), pageRequestDto.getPageSize());
        List<User> lists = sysMapper.selectAll();
        return lists;
    }

    /**
     * 获取用户列表：方式二
     *
     * @param pageRequestDto
     * @return
     */
    @Override
    public PageResult getUsersByPage1(PageRequestDto pageRequestDto) {
        //使用PageHelper.startPage只是针对接下来的一条查询语句
        PageHelper.startPage(pageRequestDto.getPageNum(), pageRequestDto.getPageSize());
        List<User> lists = sysMapper.selectAll();
        //PageInfo<User> info = new PageInfo<>(users);
        //long total = info.getTotal();
        //PageResult pageResult = new PageResult(total,users);
        return new PageResult(new PageInfo(lists).getTotal(), lists);
    }

    /**
     * 获取用户列表：根据单表条件
     *
     * @param userQueryDto
     * @return
     */
    @Override
    public PageResult getUsersBySearch1(UserQueryDto userQueryDto) {


        //对哪个实体类（表）进行筛选
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //实体类属性
        criteria.andEqualTo("nickName", userQueryDto.getNickName());
        criteria.andEqualTo("name", userQueryDto.getName());
        criteria.andEqualTo("sex", userQueryDto.getSex());
        criteria.andEqualTo("status", userQueryDto.getAccountStatus());
        criteria.andEqualTo("createTime", userQueryDto.getCreateTime());

        PageHelper.startPage(userQueryDto.getPageNum(), userQueryDto.getPageSize());
        List<User> lists = userMapper.selectByExample(example);

        PageInfo<User> info = new PageInfo<>(lists);
        long total = info.getTotal();
        PageResult pageResult = new PageResult(total, lists);
        return pageResult;
    }

    @Override
    public PageResult getUsersBySearch(UserQueryDto userQueryDto) {
        //对哪个实体类（表）进行筛选
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //实体类属性
        if (!StringUtils.isEmpty(userQueryDto.getName())) {
            criteria.andEqualTo("name", userQueryDto.getName());
        }
        if (!StringUtils.isEmpty(userQueryDto.getNickName())) {
            criteria.andEqualTo("nickName", userQueryDto.getNickName());
        }
        if (!StringUtils.isEmpty(userQueryDto.getSex())) {
            criteria.andEqualTo("sex", userQueryDto.getSex());
        }

        if (!StringUtils.isEmpty(userQueryDto.getAccountStatus())) {
            criteria.andEqualTo("status", userQueryDto.getAccountStatus());
        }

        if (null == userQueryDto.getCreateTime() || userQueryDto.getCreateTime().length == 0) {
            // TODO: 2020/6/1
        } else {
            //criteria.andEqualTo("createTime", userQueryDto.getCreateTime());
            criteria.andBetween("createTime", userQueryDto.getCreateTime()[0], userQueryDto.getCreateTime()[1]);
        }

        //先在角色-用户表中，筛选出搜索框的角色id，得出所有筛选到的用户id
        String roleId = userQueryDto.getRoleId();
        if (!StringUtils.isEmpty(roleId)) {
            Example exampleRole = new Example(RoleUser.class);
            Example.Criteria criteria1 = exampleRole.createCriteria();
            criteria1.andEqualTo("roleId", roleId);
            List<RoleUser> roleUsers = roleUserMapper.selectByExample(exampleRole);
            List<Integer> collect = roleUsers.stream().map(RoleUser::getUserId)
                    .collect(Collectors.toList());
            //获取用户表中，是筛选后的角色编号id的用户编号id
            if (null == collect || collect.isEmpty()) {
                //管理员不存在怎么办
                PageResult pageResult = new PageResult(0l, null);
                return pageResult;
            }
            criteria.andIn("id", collect);
        }

        PageHelper.startPage(userQueryDto.getPageNum(), userQueryDto.getPageSize());
        List<User> lists = userMapper.selectByExample(example);
        lists.stream().forEach(user -> {
            String sexValue = dictionaryUtils.toChinese("sex", user.getSex());
            String statusValue = dictionaryUtils.toChinese("account_status", user.getStatus());
            user.setSex(sexValue);
            user.setStatus(statusValue);
        });

        PageInfo<User> info = new PageInfo<>(lists);
        long total = info.getTotal();
        PageResult pageResult = new PageResult(total, lists);
        return pageResult;
    }

    @Override
    public ResponseResult addUser(User user) {
        int i = userMapper.insert(user);
        if (i > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "提交成功");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "提交失败");
    }

    @Override
    public ResponseResult updateUser(User user) {
        int i = userMapper.updateByPrimaryKey(user);
        if (i > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "提交成功");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "提交失败");
    }

    @Override
    public ResponseResult updateUserStatus(User user) {
        int i = userMapper.updateByPrimaryKeySelective(user);
        String msg = "";
        if (i > 0) {
            switch (user.getStatus()) {
                case "0":
                    msg = "禁用成功";
                    break;
                case "1":
                    msg = "启用成功";
                    break;
                default:
                    msg="成功";
            }
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, msg);
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "操作失败");
    }

    @Override
    public ResponseResult deleteUser(User user) {
        int i = userMapper.deleteByPrimaryKey(user);
        if (i > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "删除成功");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "删除失败");
    }

    @Override
    public ResponseResult importExcel(List list) {
        System.out.println("111111");
        return null;
    }


}
