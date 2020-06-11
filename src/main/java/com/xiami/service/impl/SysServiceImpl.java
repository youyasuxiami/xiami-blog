package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.dao.RoleUserMapper;
import com.xiami.dao.SysMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.PageRequestDto;
import com.xiami.dto.UserQueryDto;
import com.xiami.entity.RoleUser;
import com.xiami.entity.User;
import com.xiami.service.SysService;
import com.xiami.utils.DictionaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­30 17:21
 */
@Service
public class SysServiceImpl implements SysService {


}
