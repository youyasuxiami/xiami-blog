package com.xiami.service.impl;

import com.xiami.dao.TCommentReportMapper;
import com.xiami.service.TCommentReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TCommentReportServiceImpl implements TCommentReportService {

    @Resource
    private TCommentReportMapper tCommentReportMapper;

}

