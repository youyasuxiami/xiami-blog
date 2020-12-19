package com.xiami.service.impl;

import com.xiami.dao.TBlogTagsMapper;
import com.xiami.service.TBlogTagsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TBlogTagsServiceImpl implements TBlogTagsService {

    @Resource
    private TBlogTagsMapper tBlogTagsMapper;

}
