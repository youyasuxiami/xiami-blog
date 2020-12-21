package com.xiami.controller;

import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dto.SysJobForm;
import com.xiami.dto.SysJobQueryDto;
import com.xiami.entity.SysJob;
import com.xiami.entity.TType;
import com.xiami.service.SysJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/monitor")
public class JobController {

    @Autowired
    private SysJobService sysJobService;

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
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获取定时任务列表数据成功",jobList);
    }
    //
    ///**
    // * 新增/编辑
    // *
    // * @param tType
    // * @return
    // */
    @PostMapping("/addUpdateJob")
    public ResponseResult addUpdateJob(@RequestBody SysJobForm sysJobForm) {
        if (null == sysJobForm.getId()) {
            //新增
            int i = sysJobService.addJob(sysJobForm);
            if(i>0){
                return new ResponseResult(ResponseResult.CodeStatus.OK,"新增定时任务成功");
            }else{
                return new ResponseResult(ResponseResult.CodeStatus.FAIL,"新增定时任务失败");
            }
        }
        //编辑
        int j=sysJobService.updateJob(sysJobForm);
        if(j>0){
            return new ResponseResult(ResponseResult.CodeStatus.OK,"更新定时任务成功");
        }else{
            return new ResponseResult(ResponseResult.CodeStatus.FAIL,"更新定时任务失败");
        }
    }

    @DeleteMapping("/deleteJob")
    public ResponseResult deleteJob(Integer id) {
        int i = sysJobService.deleteJob(id);
        if(i>0){
            return new ResponseResult(ResponseResult.CodeStatus.OK,"删除定时任务成功");
        }else{
            return new ResponseResult(ResponseResult.CodeStatus.FAIL,"删除定时任务失败");
        }
    }

    @DeleteMapping("/deleteJobs")
    public ResponseResult deleteJobs(Integer[] ids) {
        int i=sysJobService.deleteJobs(ids);
        if(i>0){
            return new ResponseResult(ResponseResult.CodeStatus.OK,"删除定时任务成功");
        }else{
            return new ResponseResult(ResponseResult.CodeStatus.FAIL,"删除定时任务失败");
        }
    }

}
