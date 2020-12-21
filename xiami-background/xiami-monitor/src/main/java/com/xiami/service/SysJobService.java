package com.xiami.service;

import com.xiami.base.PageResult;
import com.xiami.dto.SysJobForm;
import com.xiami.dto.SysJobQueryDto;

public interface SysJobService {

    PageResult getJobList(SysJobQueryDto sysJobQueryDto);


    int addJob(SysJobForm sysJobForm);

    int updateJob(SysJobForm sysJobForm);

    int deleteJob(Integer id);

    int deleteJobs(Integer[] ids);
}
