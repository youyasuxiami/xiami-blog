package com.xiami.controller;

import com.xiami.annotation.OperatorLog;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.config.QuartzConfig;
import com.xiami.dto.PageRequestDto;
import com.xiami.dto.SysJobForm;
import com.xiami.dto.SysJobQueryDto;
import com.xiami.dto.SysOperatorLogQueryDto;
import com.xiami.entity.SysJob;
import com.xiami.entity.SysOperLog;
import com.xiami.service.SysJobService;
import com.xiami.service.SysOperLogService;
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
@RequestMapping("/monitor/operator")
@Transactional
@Slf4j
public class SysOperatorLogController {

    @Autowired
    private SysOperLogService sysOperLogService;

    /**
     * 分页显示分类数据
     *
     * @return
     */
    @GetMapping("/getOperatorLogList")
    public ResponseResult getJobList(PageRequestDto pageRequestDto, SysOperLog sysOperLog) {
        PageResult operationLogList = sysOperLogService.getOperationLogList(pageRequestDto,sysOperLog);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取操作日志成功", operationLogList);
    }

    //
    @OperatorLog("删除操作日志")
    @DeleteMapping("/deleteLog")
    public ResponseResult deleteJob(Integer id) {
        int i = sysOperLogService.deleteLog(id);
        if (i > 0) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "删除操作日志成功");
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "删除操作日志失败");
        }
    }

    //@OperatorLog("批量删除操作日志")
    @DeleteMapping("/deleteLogs")
    public ResponseResult deleteLogs(Integer[] ids) {
        int i = sysOperLogService.deleteLogs(ids);
        if (i > 0) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "批量删除操作日志成功");
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "批量删除操作日志失败");
        }
    }

}
