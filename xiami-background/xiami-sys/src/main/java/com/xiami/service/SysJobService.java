package com.xiami.service;

import com.xiami.base.PageResult;
import com.xiami.dto.SysJobQueryDto;

public interface SysJobService{

    PageResult getJobList(SysJobQueryDto sysJobQueryDto);
}
