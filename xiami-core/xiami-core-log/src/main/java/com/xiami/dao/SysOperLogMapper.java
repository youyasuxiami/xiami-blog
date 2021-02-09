package com.xiami.dao;

import com.xiami.entity.PageData;
import com.xiami.entity.SysOperLog;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface SysOperLogMapper extends MyMapper<SysOperLog> {
    int deleteLogs(Integer[] ids);

    int selectByCurrentDay();

    List<PageData> getList();

    List<PageData> getProvinceAndNum();
}