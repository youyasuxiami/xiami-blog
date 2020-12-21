package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.dao.SysJobMapper;
import com.xiami.dto.SysJobDto;
import com.xiami.dto.SysJobForm;
import com.xiami.dto.SysJobQueryDto;
import com.xiami.entity.SysJob;
import com.xiami.service.SysJobService;
import com.xiami.utils.DictionaryUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysJobServiceImpl implements SysJobService {

    @Resource
    private SysJobMapper sysJobMapper;

    @Override
    public PageResult getJobList(SysJobQueryDto sysJobQueryDto) {
        PageHelper.startPage(sysJobQueryDto.getPageNum(), sysJobQueryDto.getPageSize());
        //List<SysJob> sysJobs = sysJobMapper.getJobList(sysJobQueryDto);
        List<SysJob> sysJobs = sysJobMapper.selectAll();
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

    @Override
    public int addJob(SysJobForm sysJobForm) {
        SysJob sysJob=new SysJob();
        BeanUtils.copyProperties(sysJobForm,sysJob);
        int i = sysJobMapper.insertSelective(sysJob);
        return i;
    }

    @Override
    public int updateJob(SysJobForm sysJobForm) {
        SysJob sysJob=new SysJob();
        BeanUtils.copyProperties(sysJobForm,sysJob);
        int i = sysJobMapper.updateByPrimaryKeySelective(sysJob);
        return i;
    }

    @Override
    public int deleteJob(Integer id) {
        SysJob sysJob=new SysJob();
        sysJob.setId(id);
        int i = sysJobMapper.deleteByPrimaryKey(sysJob);
        return i;
    }

    @Override
    public int deleteJobs(Integer[] ids) {
        int i = sysJobMapper.deleteJobs(ids);
        return i;
    }
}
