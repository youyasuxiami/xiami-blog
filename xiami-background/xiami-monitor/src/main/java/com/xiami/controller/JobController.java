package com.xiami.controller;

import com.xiami.annotation.OperatorLog;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.config.QuartzConfig;
import com.xiami.dto.SysJobForm;
import com.xiami.dto.SysJobQueryDto;
import com.xiami.entity.SysJob;
import com.xiami.service.SysJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­16 12:26
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@EnableScheduling
@RequestMapping("/monitor/job")
@Transactional
@Slf4j
public class JobController {

    @Autowired
    private SysJobService sysJobService;

    @Autowired
    private QuartzConfig quartzConfig;

    //@GetMapping("/getTypes")
    //public ResponseResult getTypes() {
    //    return tTypeService.getTypes();
    //}

    /**
     * 分页显示分类数据
     *
     * @return
     */
    @GetMapping("/getJobList")
    public ResponseResult getJobList(SysJobQueryDto sysJobQueryDto) {
        PageResult jobList = sysJobService.getJobList(sysJobQueryDto);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取定时任务列表数据成功", jobList);
    }

    /**
     * 新增/编辑定时任务
     *
     * @param sysJobForm
     * @return
     */
    @OperatorLog("新增/编辑定时任务")
    @PostMapping("/addUpdateJob")
    public ResponseResult addUpdateJob(@RequestBody SysJobForm sysJobForm) {
        if (null == sysJobForm.getId()) {
            //新增
            try {
                int i = sysJobService.addJob(sysJobForm);
                if (i > 0) {
                    return new ResponseResult(ResponseResult.CodeStatus.OK, "新增定时任务成功");
                }
                return new ResponseResult(ResponseResult.CodeStatus.FAIL, "新增定时任务失败");
            } catch (DuplicateKeyException e) {
                return new ResponseResult(ResponseResult.CodeStatus.FAIL, "任务名称和任务组别已存在，请重新输入");
            }
        }
        //编辑
        SysJob sysJob=new SysJob();
        BeanUtils.copyProperties(sysJobForm,sysJob);
        int j = sysJobService.updateSysJobForm(sysJob);
        if (j > 0) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "更新定时任务成功");
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "更新定时任务失败");
        }
    }

    @OperatorLog("删除定时任务")
    @DeleteMapping("/deleteJob")
    public ResponseResult deleteJob(SysJobForm sysJobForm) {
        int i = sysJobService.deleteJob(sysJobForm);
        if (i > 0) {
            //删除定时任务成功后，停止该定时任务
            //try {
            quartzConfig.stopJob(sysJobForm.getJobName(), sysJobForm.getJobGroup());
            return new ResponseResult(ResponseResult.CodeStatus.OK, "定时任务成功");
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "删除定时任务失败");
        }
    }

    @OperatorLog("批量删除定时任务")
    @DeleteMapping("/deleteJobs")
    public ResponseResult deleteJobs(Integer[] ids) {
        int i = sysJobService.deleteJobs(ids);
        if (i > 0) {
            log.info("删除定时任务成功");
            return new ResponseResult(ResponseResult.CodeStatus.OK, "删除定时任务成功");
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "删除定时任务失败");
        }
    }

    @OperatorLog("启动定时任务")
    @PostMapping("/startJob")
    public ResponseResult startJob(@RequestBody SysJobForm sysJobForm) {
        //开启定时任务
        quartzConfig.start(sysJobForm);
        //开启成功，更新该id的定时任务的状态
        sysJobForm.setJobStatus("2");
        String cronExpression = sysJobForm.getCronExpression();
        try {
            CronExpression cronExpression1 = new CronExpression(cronExpression);
            Date nextValidTimeAfter = cronExpression1.getNextValidTimeAfter(new Date());
            SysJob sysJob=new SysJob();
            BeanUtils.copyProperties(sysJobForm,sysJob);
            sysJob.setNextTime(nextValidTimeAfter);
            sysJob.setJobStatus("2");
            sysJobService.updateSysJobForm(sysJob);
            log.info("开启定时任务成功");
            return new ResponseResult(ResponseResult.CodeStatus.OK, "开启定时任务成功");
        } catch (ParseException e) {
            log.error("生成CronExpression表达式失败：{}",e);
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "开启定时任务失败");
        }

    }

    @OperatorLog("停止定时任务")
    @PostMapping("/stopJob")
    public ResponseResult stopJob(@RequestBody SysJobForm sysJobForm) {
        //停止定时任务
        quartzConfig.stopJob(sysJobForm.getJobName(), sysJobForm.getJobGroup());
        sysJobForm.setJobStatus("1");//更改状态为未发布
        SysJob sysJob=new SysJob();
        BeanUtils.copyProperties(sysJobForm,sysJob);
        sysJobService.updateSysJobForm(sysJob);
        log.info("停止定时任务成功");
        return new ResponseResult(ResponseResult.CodeStatus.OK, "停止定时任务成功");
    }

    @OperatorLog("恢复定时任务")
    @PostMapping("/resumeJob")
    public ResponseResult resumeJob(@RequestBody SysJobForm sysJobForm) {
        //停止定时任务
        quartzConfig.resumeJob(sysJobForm.getJobName(), sysJobForm.getJobGroup(), sysJobForm.getId());
        sysJobForm.setJobStatus("2");
        SysJob sysJob=new SysJob();
        BeanUtils.copyProperties(sysJobForm,sysJob);
        sysJobService.updateSysJobForm(sysJob);
        log.info("恢复定时任务成功");
        return new ResponseResult(ResponseResult.CodeStatus.OK, "恢复定时任务成功");
    }
}
