package com.xiami.blog.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xiami.blog.dao.TBlogMapper;

@Service
public class TBlogService {

    @Resource
    private TBlogMapper tBlogMapper;

}

