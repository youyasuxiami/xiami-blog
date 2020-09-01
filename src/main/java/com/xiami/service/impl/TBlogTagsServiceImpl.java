package com.xiami.service.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.xiami.dao.TBlogTagsMapper;
import com.xiami.service.TBlogTagsService;

@Service
public class TBlogTagsServiceImpl implements TBlogTagsService {

    @Resource
    private TBlogTagsMapper tBlogTagsMapper;

}
