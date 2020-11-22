package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.Constant;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.RoleMapper;
import com.xiami.dao.RoleUserMapper;
import com.xiami.dao.SysMapper;
import com.xiami.dao.TBlogMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.PageRequestDto;
import com.xiami.dto.UserDto;
import com.xiami.dto.UserQueryDto;
import com.xiami.entity.RoleUser;
import com.xiami.entity.TBlog;
import com.xiami.entity.User;
import com.xiami.service.UserService;
import com.xiami.utils.AccountSecurityUtils;
import com.xiami.utils.BeanUtil;
import com.xiami.utils.DictionaryUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private TBlogMapper tBlogMapper;

    public final static String EXCEL_PATH_PREFIX = "static/upload/excels";
    public final static String PATH = new UserServiceImpl().getAbsolutePath();


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

    @Transactional
    @Override
    public PageResult getUsersBySearch(UserQueryDto userQueryDto) {
        //对实体类（表）进行筛选
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //实体类属性
        if (!StringUtils.isEmpty(userQueryDto.getName())) {//模糊查询
            criteria.andLike("name", "%" + userQueryDto.getName() + "%");//实体类属性
        }
        if (!StringUtils.isEmpty(userQueryDto.getNickName())) {
            criteria.andLike("nickName", "%" + userQueryDto.getNickName() + "%");
        }
        if (!StringUtils.isEmpty(userQueryDto.getSex())) {
            criteria.andEqualTo("sex", userQueryDto.getSex());
        }

        if (!StringUtils.isEmpty(userQueryDto.getAccountStatus())) {
            criteria.andEqualTo("status", userQueryDto.getAccountStatus());
        }

        if (null != userQueryDto.getCreateTime() && userQueryDto.getCreateTime().length != 0) {
            //criteria.andEqualTo("createTime", userQueryDto.getCreateTime());
            criteria.andBetween("createTime", userQueryDto.getCreateTime()[0], userQueryDto.getCreateTime()[1] + " 23:59:59");
        }
        //先在角色-用户表中，筛选出搜索框的角色id，得出所有筛选到的用户id
        Integer[] ids = userQueryDto.getRoleIds();
        if (null != ids && ids.length != 0) {
            Example exampleRole = new Example(RoleUser.class);
            Example.Criteria criteria1 = exampleRole.createCriteria();
            criteria1.andIn("roleId", Arrays.asList(ids));
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

    @Transactional
    @Override
    public ResponseResult addUser(UserDto userDto) {
        User user = new User();
        try {
            BeanUtils.copyProperties(userDto, user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "提交失败");
        }
        if (null != userDto.getRoleIds() && userDto.getRoleIds().length != 0) {//选了角色
            try {
                //插入用户
                int i = userMapper.insert(user);
                //插入角色-用户表,先获取所有的插入数据
                List<RoleUser> roleUserList = new ArrayList<>();
                List<Integer> list = Arrays.asList(userDto.getRoleIds());
                for (int i1 = 0; i1 < list.size(); i1++) {
                    RoleUser roleUser = new RoleUser();
                    roleUser.setUserId(user.getId());
                    roleUser.setRoleId(list.get(i1));
                    roleUser.setCreateTime(new Date());
                    roleUser.setUpdateTime(new Date());
                    roleUserList.add(roleUser);
                }
                int i1 = roleUserMapper.insertList(roleUserList);
                return ResponseResult.getResponseResult(i > 0 && i1 > 0, "提交成功", "提交失败");
            } catch (DuplicateKeyException e) {
                String code = getDuplicateKeyExceptionMsg(e);
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "数据库中已经存在用户名为 " + code + " 的数据，请重新导入");
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "提交失败");
            }
        } else {//没有选择角色
            try {
                int i = userMapper.insert(user);
                return ResponseResult.getResponseResult(i > 0, "提交成功", "提交失败");
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "提交失败");
            }
        }
    }

    @Transactional
    @Override
    public ResponseResult updateUser(UserDto userDto) {
        User user = new User();
        try {
            BeanUtils.copyProperties(userDto, user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "提交失败");
        }
        //先获取用户-角色表中：该用户的所有角色数据
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(userDto.getId());
        List<Integer> ids = roleUserMapper.select(roleUser)
                .stream()
                .map(RoleUser::getId)
                .collect(Collectors.toList());
        //判断该用户在用户-角色表中，有没有数据
        if (null == ids || ids.size() == 0) {
            // TODO: 2020/7/12  没数据
        } else {//有数据，先删除数据
            try {
                roleUserMapper.deleteExist(ids);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "提交失败");
            }
        }

        try {
            //再更新用户表
            int i1 = userMapper.updateByPrimaryKey(user);
            //最后更新用户-角色表
            List<RoleUser> roleUserList = new ArrayList<>();
            List<Integer> list = Arrays.asList(userDto.getRoleIds());
            for (int i = 0; i < list.size(); i++) {
                RoleUser roleUser1 = new RoleUser();
                roleUser1.setUserId(user.getId());
                roleUser1.setRoleId(list.get(i));
                roleUser1.setCreateTime(new Date());
                roleUser1.setUpdateTime(new Date());
                roleUserList.add(roleUser1);
            }
            int i2 = roleUserMapper.insertList(roleUserList);
            return ResponseResult.getResponseResult(i1 > 0 && i2 > 0, "提交成功", "提交失败");
        } catch (DuplicateKeyException e) {
            String code = getDuplicateKeyExceptionMsg(e);
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "数据库中已经存在用户名为 " + code + " 的数据，请重新导入");
        }
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
                    msg = "成功";
            }
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, msg);
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "操作失败");
    }

    @Transactional
    @Override
    public ResponseResult deleteUser(Integer id) {
        User user = new User();
        user.setId(id);
        int i = userMapper.deleteByPrimaryKey(user);
        //根据该用户，先查用户-角色表的数据
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(id);
        int delete = roleUserMapper.delete(roleUser);
        if (i > 0 && delete > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "删除成功");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "删除失败");
    }

    @Transactional
    @Override
    public ResponseResult importExcel(List<List<Object>> dataList) {
        List<User> list = new ArrayList<>();
        if (dataList.size() < 2) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "模板格式错误，请重新导入模板");
        } else if (dataList.size() == 2) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "模板的数据为空，请重新导入模板");
        } else {
            for (int i = 0; i < dataList.size(); i++) {
                User user = new User();
                if (i == 0) {//校验第一行表头
                    if (!"用户表".equals(String.valueOf(dataList.get(0).get(0)))) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第一行模板格式错误，请重新导入模板");
                    }
                    continue;
                }
                if (i == 1) {//校验第二行表头
                    if (!"用户表".equals(String.valueOf(dataList.get(0).get(0)))) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第1行模板格式错误，请重新导入模板");
                    }

                    if (dataList.get(1).size() < 9) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第2行模板格式错误，请重新导入模板");
                    }
                    if (!"序号".equals(String.valueOf(dataList.get(1).get(0)))) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第2行模板格式错误，请重新导入模板");
                    }
                    if (!"用户名".equals(String.valueOf(dataList.get(1).get(1)))) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第2行模板格式错误，请重新导入模板");
                    }
                    if (!"昵称".equals(String.valueOf(dataList.get(1).get(2)))) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第2行模板格式错误，请重新导入模板");
                    }
                    if (!"性别".equals(String.valueOf(dataList.get(1).get(3)))) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第2行模板格式错误，请重新导入模板");
                    }
                    if (!"年龄".equals(String.valueOf(dataList.get(1).get(4)))) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第2行模板格式错误，请重新导入模板");
                    }
                    if (!"联系方式".equals(String.valueOf(dataList.get(1).get(5)))) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第2行模板格式错误，请重新导入模板");
                    }
                    if (!"电子邮箱".equals(String.valueOf(dataList.get(1).get(6)))) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第2行模板格式错误，请重新导入模板");
                    }
                    if (!"账号状态".equals(String.valueOf(dataList.get(1).get(7)))) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第2行模板格式错误，请重新导入模板");
                    }
                    if (!"备注".equals(String.valueOf(dataList.get(1).get(8)))) {
                        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第2行模板格式错误，请重新导入模板");
                    }
                    continue;
                }

                //开始校验数据
                if (dataList.get(i).size() < 8) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第" + i + "行数据不完整，请重新导入模板");
                }
                if (StringUtils.isEmpty(dataList.get(i).get(1))) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第" + i + "行用户名不能为空，请重新导入模板");
                }
                if (StringUtils.isEmpty(dataList.get(i).get(2))) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第" + i + "行昵称不能为空，请重新导入模板");
                }
                if (StringUtils.isEmpty(dataList.get(i).get(3))) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第" + i + "行性别不能为空，请重新导入模板");
                }
                if (StringUtils.isEmpty(dataList.get(i).get(4))) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第" + i + "行年龄不能为空，请重新导入模板");
                }
                if (StringUtils.isEmpty(dataList.get(i).get(5))) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第" + i + "行联系方式不能为空，请重新导入模板");
                }
                if (StringUtils.isEmpty(dataList.get(i).get(6))) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第" + i + "行电子邮箱不能为空，请重新导入模板");
                }
                if (StringUtils.isEmpty(dataList.get(i).get(7))) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "第" + i + "行账号状态不能为空，请重新导入模板");
                }
                //if (StringUtils.isEmpty(dataList.get(i).get(8))) {
                //    return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "备注不能为空，请重新导入模板");
                //}


                //赋值
                user.setName(String.valueOf(dataList.get(i).get(1)));
                user.setNickName(String.valueOf(dataList.get(i).get(2)));
                if ("男".equals(dataList.get(i).get(3))) {
                    user.setSex("0");
                } else {
                    user.setSex("1");
                }
                user.setAge(String.valueOf(dataList.get(i).get(4)));
                user.setPhone(String.valueOf(dataList.get(i).get(5)));
                user.setEmail(String.valueOf(dataList.get(i).get(6)));
                if ("禁用".equals(dataList.get(i).get(7))) {
                    user.setStatus("0");
                } else {
                    user.setStatus("1");
                }
                //if(dataList.get(i).size()==8){
                //    user.setPs("");
                //}else{
                user.setPs(String.valueOf(dataList.get(i).get(8)));
                //}
                user.setAvatar(Constant.DEFAULT_AVATAR);//默认头像
                String newPass = new Md5Hash("123456", user.getName(), 1024).toBase64();
                user.setPassword(newPass);//默认密码

                user.setCreateTime(new Date());
                user.setLoginTime(new Date());
                user.setUpdateTime(new Date());

                list.add(user);
            }
            //List<Integer> integers = userMapper.insertUsers(list);
            int i = 0;
            try {
                i = userMapper.insertUsers(list);
                if (i > 0) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.OK, "导入数据成功");
                }
            } catch (DuplicateKeyException e) {
                String code = getDuplicateKeyExceptionMsg(e);
                //获取唯一索引的名称
                //String s2 = message2.split("\n")[0].split("\'")[3];
                //jsonUtil.setFlag(false);
                //jsonUtil.setMsg("选择的号码已存在，重复数据为:"+code);
                //jsonUtil.setCode(EXEITEATA);
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "数据库中已经存在用户名为 " + code + " 的数据，请重新导入");
            }
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "导入数据失败");
    }

    /**
     * 导出当页数据用户表excel
     *
     * @param out
     * @param userQueryDto
     * @throws Exception
     */
    @Override
    public void exportUserToExcel(OutputStream out, UserQueryDto userQueryDto) {
        //获取用户列表中所有的数据
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

        //if (!StringUtils.isEmpty(userQueryDto.getAccountStatus())&&!"null".equals(userQueryDto.getAccountStatus())) {
        if (!StringUtils.isEmpty(userQueryDto.getAccountStatus())) {
            criteria.andEqualTo("status", userQueryDto.getAccountStatus());
        }

        if (null == userQueryDto.getCreateTime() || userQueryDto.getCreateTime().length == 0) {
            // TODO: 2020/6/1
        } else {
            criteria.andBetween("createTime", userQueryDto.getCreateTime()[0], userQueryDto.getCreateTime()[1]);
        }

        //先在角色-用户表中，筛选出搜索框的角色id，得出所有筛选到的用户id
        Integer[] ids = userQueryDto.getRoleIds();
        if (null != ids && ids.length != 0) {
            Example exampleRole = new Example(RoleUser.class);
            Example.Criteria criteria1 = exampleRole.createCriteria();
            criteria1.andIn("roleId", Arrays.asList(ids));
            List<RoleUser> roleUsers = roleUserMapper.selectByExample(exampleRole);
            List<Integer> collect = roleUsers.stream().map(RoleUser::getUserId)
                    .collect(Collectors.toList());
            //获取用户表中，是筛选后的角色编号id的用户编号id
            if (null == collect || collect.isEmpty()) {
                // TODO: 2020/6/26 管理员不存在怎么办
                //管理员不存在怎么办
                //PageResult pageResult = new PageResult(0l, null);
                //return pageResult;
                //return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "管理员不存在");
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

        String title = "用户列表";

        // 1、创建一个工作簿 07
        Workbook workbook = new SXSSFWorkbook();
        // 2、创建一个工作表
        Sheet sheet = workbook.createSheet(title);
        Row row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("用户表");
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("序号");
        row1.createCell(1).setCellValue("用户名");
        row1.createCell(2).setCellValue("昵称");
        row1.createCell(3).setCellValue("性别");
        row1.createCell(4).setCellValue("年龄");
        row1.createCell(5).setCellValue("联系方式");
        row1.createCell(6).setCellValue("电子邮箱");
        row1.createCell(7).setCellValue("备注");
        row1.createCell(8).setCellValue("账号状态");

        Row row = null;
        Cell cell = null;
        for (int i = 0; i < lists.size(); i++) {
            row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(lists.get(i).getName());
            row.createCell(2).setCellValue(lists.get(i).getNickName());
            row.createCell(3).setCellValue(lists.get(i).getSex());
            row.createCell(4).setCellValue(lists.get(i).getAge());
            row.createCell(5).setCellValue(lists.get(i).getPhone());
            row.createCell(6).setCellValue(lists.get(i).getEmail());
            row.createCell(7).setCellValue(lists.get(i).getPs());
            row.createCell(8).setCellValue(lists.get(i).getStatus());

        }
        // 输出
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出全部数据用户表excel
     *
     * @param out
     * @param userQueryDto
     * @throws Exception
     */
    @Override
    public void exportAllUserToExcel(OutputStream out, UserQueryDto userQueryDto) {
        //获取用户列表中所有的数据
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

        //if (!StringUtils.isEmpty(userQueryDto.getAccountStatus())&&!"null".equals(userQueryDto.getAccountStatus())) {
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
        Integer[] ids = userQueryDto.getRoleIds();
        if (null != ids && ids.length != 0) {
            Example exampleRole = new Example(RoleUser.class);
            Example.Criteria criteria1 = exampleRole.createCriteria();
            criteria1.andIn("roleId", Arrays.asList(ids));
            List<RoleUser> roleUsers = roleUserMapper.selectByExample(exampleRole);
            List<Integer> collect = roleUsers.stream().map(RoleUser::getUserId)
                    .collect(Collectors.toList());
            //获取用户表中，是筛选后的角色编号id的用户编号id
            if (null == collect || collect.isEmpty()) {
                // TODO: 2020/6/26 管理员不存在怎么办
                //管理员不存在怎么办
                //PageResult pageResult = new PageResult(0l, null);
                //return pageResult;
                //return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "管理员不存在");
            }
            criteria.andIn("id", collect);
        }

        //PageHelper.startPage(userQueryDto.getPageNum(), userQueryDto.getPageSize());
        List<User> lists = userMapper.selectByExample(example);
        lists.stream().forEach(user -> {
            String sexValue = dictionaryUtils.toChinese("sex", user.getSex());
            String statusValue = dictionaryUtils.toChinese("account_status", user.getStatus());
            user.setSex(sexValue);
            user.setStatus(statusValue);
        });

        String title = "用户列表";

        // 1、创建一个工作簿 07
        Workbook workbook = new SXSSFWorkbook();
        // 2、创建一个工作表
        Sheet sheet = workbook.createSheet(title);
        Row row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("用户表");
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("序号");
        row1.createCell(1).setCellValue("用户名");
        row1.createCell(2).setCellValue("昵称");
        row1.createCell(3).setCellValue("性别");
        row1.createCell(4).setCellValue("年龄");
        row1.createCell(5).setCellValue("联系方式");
        row1.createCell(6).setCellValue("电子邮箱");
        row1.createCell(7).setCellValue("备注");
        row1.createCell(8).setCellValue("账号状态");

        Row row = null;
        Cell cell = null;
        for (int i = 0; i < lists.size(); i++) {
            row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(lists.get(i).getName());
            row.createCell(2).setCellValue(lists.get(i).getNickName());
            row.createCell(3).setCellValue(lists.get(i).getSex());
            row.createCell(4).setCellValue(lists.get(i).getAge());
            row.createCell(5).setCellValue(lists.get(i).getPhone());
            row.createCell(6).setCellValue(lists.get(i).getEmail());
            row.createCell(7).setCellValue(lists.get(i).getPs());
            row.createCell(8).setCellValue(lists.get(i).getStatus());

        }
        // 输出
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 相对路径转绝对路径
     *
     * @return
     */
    private String getAbsolutePath() {
        String fileDirPath = new String("src/main/resources/" + EXCEL_PATH_PREFIX);
        File fileDir = new File(fileDirPath);
        return fileDir.getAbsolutePath() + File.separator;
    }

    /**
     * 批量删除用户
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public ResponseResult deleteUsers(Integer[] ids) {
        try {
            int i = userMapper.deleteUsers(ids);
            // TODO: 2020/7/12 需要先查再删除？
            //List<RoleUser> roleUserList = roleUserMapper.selectByUserIds(ids);
            int i1 = roleUserMapper.deleteByUserIds(ids);
            return ResponseResult.getResponseResult(i > 0 && i1 > 0, "提交成功", "提交失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "删除用户失败");
        }
    }

    @Override
    public ResponseResult getRoles() {
        try {
            List<Map<String, Object>> maps = roleMapper.selectRoles();
            return new ResponseResult(ResponseResult.CodeStatus.OK, "获取所有角色信息成功", maps);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "获取所有角色信息失败");
        }
    }

    @Override
    public ResponseResult getCheckedRoles(Integer id) {
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(id);
        //获取该用户的所有角色id
        List<Integer> select = roleUserMapper.select(roleUser)
                .stream()
                .map(RoleUser::getRoleId)
                .collect(Collectors.toList());
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取该用户的角色信息成功", select);
    }

    /**
     * 获得非唯一索引的异常信息
     *
     * @param e
     * @return
     */
    private String getDuplicateKeyExceptionMsg(DuplicateKeyException e) {
        String[] code1 = BeanUtil.getCode(e);
        String code = code1[1];
        if (code.contains("-")) {
            //如果包含- 则组合索引生效，这里是去除重复的工单数据
            String[] split = code.split("-");
            code = split[0];
        }
        return code;
    }

    @Transactional
    @Override
    public ResponseResult addMember(UserDto userDto) {
        User user = new User();
        try {
            BeanUtils.copyProperties(userDto, user);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setLoginTime(new Date());
            user.setStatus("1");
            //密码解密
            String PassworddJieMi = AccountSecurityUtils.decrypt(userDto.getPassword().trim());
            //密码加密
            String PassworddJiaMi = new Md5Hash(PassworddJieMi, user.getName(), 1024).toBase64();
            user.setPassword(PassworddJiaMi);
            user.setAvatar("http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "提交失败");
        }
        try {
            //插入用户
            int i = userMapper.insert(user);
            //插入角色-用户表,先获取所有的插入数据
            RoleUser roleUser = new RoleUser();
            roleUser.setUserId(user.getId());
            roleUser.setRoleId(71);
            roleUser.setCreateTime(new Date());
            roleUser.setUpdateTime(new Date());
            int i1 = roleUserMapper.insert(roleUser);
            return ResponseResult.getResponseResult(i > 0 && i1 > 0, "注册成功", "注册失败");
        } catch (DuplicateKeyException e) {
            String code = getDuplicateKeyExceptionMsg(e);
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "数据库中已经存在用户名为 " + code + " 的数据，请重新输入信息");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "注册失败");
        }
    }

    @Override
    public ResponseResult getUserInfo(Integer blogId) {
        TBlog tBlog = tBlogMapper.selectByPrimaryKey(blogId);
        User user=new User();
        user.setId(tBlog.getUserId());
        User user1 = userMapper.selectOne(user);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取账号信息成功",user1);
    }

    @Override
    public String getPassword(String username) {
        User user1=new User();
        user1.setName(username);
        User user = userMapper.selectOne(user1);
        if(user==null){
            return null;
        }
        String password=user.getPassword();
        return password;
    }

    @Override
    public User getUserByName(String name) {
        User user1=new User();
        user1.setName(name);
        User user = userMapper.selectOne(user1);
        return user;
    }
}

