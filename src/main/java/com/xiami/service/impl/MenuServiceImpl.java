package com.xiami.service.impl;

import com.xiami.dao.MenuMapper;
import com.xiami.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­06­02 23:13
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;
}
