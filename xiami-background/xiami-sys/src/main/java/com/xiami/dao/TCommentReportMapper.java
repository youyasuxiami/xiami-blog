package com.xiami.dao;

import com.xiami.dto.TCommentReportListDto;
import com.xiami.dto.TcommentReportQueryDto;
import com.xiami.entity.TCommentReport;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TCommentReportMapper extends MyMapper<TCommentReport> {
    void updateStatus(Integer id);

    List<TCommentReportListDto> selectBySearch(TcommentReportQueryDto tcommentReportQueryDto);
}