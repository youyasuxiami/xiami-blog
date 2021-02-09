package com.xiami.service;

import com.xiami.base.PageResult;
import com.xiami.dto.PageRequestDto;
import com.xiami.entity.PageData;
import com.xiami.entity.SysOperLog;

import java.util.List;

public interface SysOperLogService{


    PageResult getOperationLogList(PageRequestDto pageRequestDto, SysOperLog sysOperLog);

    int deleteLog(Integer id);

    int deleteLogs(Integer[] ids);

    int getOnlineNum();

    List<PageData> getOperationLogs();

    List<PageData> getProvinceAndNum();
}
