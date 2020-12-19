package com.xiami.controller;

import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dto.SysJobQueryDto;
import com.xiami.service.SysJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    //@PostMapping("/addType")
    //public ResponseResult addType(@RequestBody TType tType) {
    //    if (null == tType.getId()) {
    //        //新增
    //        return tTypeService.addType(tType);
    //    }
    //    //编辑
    //    return tTypeService.updateType(tType);
    //}
    //
    //@DeleteMapping("/deleteType")
    //public ResponseResult deleteType(Integer id) {
    //    return tTypeService.deleteType(id);
    //}
    //
    //@DeleteMapping("/deleteTypes")
    //public ResponseResult deleteTypes(Integer[] ids) {
    //    return tTypeService.deleteTypes(ids);
    //}

}
