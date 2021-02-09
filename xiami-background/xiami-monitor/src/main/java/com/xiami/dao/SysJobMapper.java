package com.xiami.dao;


import com.xiami.dto.SysJobQueryDto;
import com.xiami.entity.SysJob;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface SysJobMapper extends MyMapper<SysJob> {

    List<SysJob> getJobList(SysJobQueryDto sysJobQueryDto);


    int deleteJobs(Integer[] ids);

    int updateSysJob(SysJob sysJob);

    int updateTimes(SysJob sysJob);
}