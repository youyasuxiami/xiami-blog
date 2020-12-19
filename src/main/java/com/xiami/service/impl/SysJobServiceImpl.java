package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dto.SysJobDto;
import com.xiami.dto.SysJobQueryDto;
import com.xiami.dto.TypeQueryDto;
import com.xiami.entity.Role;
import com.xiami.entity.SysJob;
import com.xiami.utils.DictionaryUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.xiami.dao.SysJobMapper;
import com.xiami.service.SysJobService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysJobServiceImpl implements SysJobService {

    @Resource
    private SysJobMapper sysJobMapper;

    @Override
    public PageResult getJobList(SysJobQueryDto sysJobQueryDto) {
        PageHelper.startPage(sysJobQueryDto.getPageNum(), sysJobQueryDto.getPageSize());
        List<SysJob> sysJobs = sysJobMapper.getJobList(sysJobQueryDto);
        List<SysJobDto> sysJobDtoList = new ArrayList<>();
        for (SysJob sysJob : sysJobs) {
            SysJobDto sysJobDto=new SysJobDto();
            BeanUtils.copyProperties(sysJob,sysJobDto);
            String job_status = DictionaryUtils.toChinese("job_status", sysJob.getJobStatus());
            sysJobDto.setJobStatusName(job_status);
            sysJobDtoList.add(sysJobDto);
        }

        PageInfo<SysJobDto> info = new PageInfo<>(sysJobDtoList);
        long total = info.getTotal();
        PageResult pageResult = new PageResult(total, sysJobDtoList);
        return pageResult;
    }
}
