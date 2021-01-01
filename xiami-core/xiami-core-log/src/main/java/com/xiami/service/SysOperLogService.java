package com.xiami.service;

import com.xiami.base.PageResult;
import com.xiami.dto.PageRequestDto;
import com.xiami.entity.SysOperLog;

public interface SysOperLogService{


    PageResult getOperationLogList(PageRequestDto pageRequestDto, SysOperLog sysOperLog);
}
