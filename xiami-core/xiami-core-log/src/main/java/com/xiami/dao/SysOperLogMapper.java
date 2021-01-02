package com.xiami.dao;

import com.xiami.entity.SysOperLog;
import tk.mybatis.mapper.MyMapper;

public interface SysOperLogMapper extends MyMapper<SysOperLog> {
    int deleteLogs(Integer[] ids);
}