package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.dao.SysOperLogMapper;
import com.xiami.dto.PageRequestDto;
import com.xiami.entity.SysOperLog;
import com.xiami.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SysOperLogServiceImpl implements SysOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;


    @Override
    public PageResult getOperationLogList(PageRequestDto pageRequestDto, SysOperLog sysOperLog) {
        PageHelper.startPage(pageRequestDto.getPageNum(),pageRequestDto.getPageSize());
        Example example=new Example(SysOperLog.class);
        Example.Criteria criteria = example.createCriteria();

        //模糊查询
        if (!StringUtils.isEmpty(sysOperLog.getTitle())) {
            criteria.andLike("title", "%" + sysOperLog.getTitle() + "%");//第一个参数是实体类属性
        }
        if (!StringUtils.isEmpty(sysOperLog.getOperName())) {
            criteria.andLike("operName", "%" + sysOperLog.getOperName() + "%");
        }
        if (null != sysOperLog.getCreateTime() && sysOperLog.getCreateTime().length != 0) {
            criteria.andBetween("operTime", sysOperLog.getCreateTime()[0], sysOperLog.getCreateTime()[1] + " 23:59:59");
        }
        example.setOrderByClause("oper_time DESC");

        List<SysOperLog> sysOperLogList = sysOperLogMapper.selectByExample(example);
        PageInfo pageInfo=new PageInfo(sysOperLogList);
        long total = pageInfo.getTotal();
        PageResult pageResult=new PageResult(total,sysOperLogList);
        return pageResult;
    }

    @Override
    public int deleteLog(Integer id) {
        SysOperLog sysOperLog=new SysOperLog();
        sysOperLog.setId(id);
        int i = sysOperLogMapper.deleteByPrimaryKey(sysOperLog);
        return i;
    }

    @Override
    public int deleteLogs(Integer[] ids) {
        int i = sysOperLogMapper.deleteLogs(ids);
        return i;
    }
}
