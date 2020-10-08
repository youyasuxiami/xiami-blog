package com.xiami.dao;

import com.xiami.entity.TCommentReport;
import tk.mybatis.mapper.MyMapper;

public interface TCommentReportMapper extends MyMapper<TCommentReport> {
    void updateStatus(Integer id);
}