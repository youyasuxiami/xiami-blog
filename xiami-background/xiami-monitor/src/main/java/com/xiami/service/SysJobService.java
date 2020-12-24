package com.xiami.service;

import com.xiami.base.PageResult;
import com.xiami.dto.SysJobForm;
import com.xiami.dto.SysJobQueryDto;
import com.xiami.entity.SysJob;

public interface SysJobService {

    PageResult getJobList(SysJobQueryDto sysJobQueryDto);


    int addJob(SysJobForm sysJobForm);

    int updateSysJobForm(SysJobForm sysJobForm);

    int updateSysJob(SysJob sysJob);

    int deleteJob(Integer id);

    int deleteJobs(Integer[] ids);


}
