package com.xiami.service.impl;

import com.xiami.dao.PermissionMapper;
import com.xiami.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­11­19 9:08
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    PermissionMapper permissionMapper;

}
