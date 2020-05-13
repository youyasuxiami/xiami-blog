package com.xiami.service.impl;

import com.xiami.dao.TBlogMapper;
import com.xiami.service.TBlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author：郑锦
 * Date：2020­05­14 0:39
 * Description：<描述>
 */
@Service
public class TBlogServiceImpl implements TBlogService {

    @Resource
    private TBlogMapper tBlogMapper;
}
