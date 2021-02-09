package com.xiami.controller;

import com.xiami.base.ResponseResult;
import com.xiami.dao.TBlogMapper;
import com.xiami.dto.BlogTagDto;
import com.xiami.dto.BlogTypeDto;
import com.xiami.dto.HotBlogTypeDto;
import com.xiami.entity.PageData;
import com.xiami.entity.SysOperLog;
import com.xiami.service.SysOperLogService;
import com.xiami.service.TBlogService;
import com.xiami.service.UserService;
import com.xiami.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2021­02­02 14:12
 */
@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@EnableScheduling
public class BackGroundHomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private SysOperLogService sysOperLogService;

    @Autowired
    private TBlogService tBlogService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 1、获得本月新增用户数和今日在线数
     *
     * @return
     */
    @RequestMapping("/getNumAndOnlineNum")
    public ResponseResult getAddNumAndOnlineNum() {
        if (redisUtil.get("addNum") == null || redisUtil.get("onlineNum") == null) {
            updateAddNumAndOnlineNum();
        }
        Map map = new HashMap<>();
        map.put("addNum", redisUtil.get("addNum"));
        map.put("onlineNum", sysOperLogService.getOnlineNum());
        redisUtil.incr("views",1);
        map.put("views", redisUtil.get("views"));
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获得本月新增用户数和今日在线数成功", map);
    }

    /**
     * 1、更新redis中的本月新增用户数1
     *
     * @return
     */
    @RequestMapping("/updateAddNumAndOnlineNum")
    public void updateAddNumAndOnlineNum() {
        log.info("更新redis中的本月新增用户数————开始");
        redisUtil.set("addNum", userService.getAddNum());
        log.info("更新redis中的本月新增用户数————结束");
    }

    /**
     *2、获得所有文章类别和对应的文章数
     *
     * @return
     */
    @RequestMapping("/getBlogTypeAndNum")
    public ResponseResult getBlogTypeAndNum() {
        if (redisUtil.get("blogTypeAndNum") == null) {
            updateBlogTypeAndNum();
        }
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获得本月新增用户数和今日在线数成功",redisUtil.get("blogTypeAndNum"));
    }

    /**
     *2、更新redis中的所有文章类别和对应的文章数
     *
     * @return
     */
    @RequestMapping("/updateBlogTypeAndNum")
    public void updateBlogTypeAndNum() {
        log.info("更新redis中的所有文章类别和对应的文章数————开始");
        List<PageData> blogTypeAndNum = tBlogService.getBlogTypeAndNum();
        redisUtil.set("blogTypeAndNum", blogTypeAndNum);
        log.info("更新redis中的所有文章类别和对应的文章数————结束");
    }

    /**
     *3、获得所有文章标签和对应的文章数
     *
     * @return
     */
    @RequestMapping("/getBlogTagAndNum")
    public ResponseResult getBlogTagAndNum() {
        if (redisUtil.get("blogTypeAndNum") == null) {
            updateBlogTagAndNum();
        }
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获得所有文章标签和对应的文章数成功",redisUtil.get("blogTypeAndNum"));
    }

    /**
     * 3、更新redis中的获得所有文章标签和对应的文章数
     *
     * @return
     */
    @RequestMapping("/updateBlogTagAndNum")
    public void updateBlogTagAndNum() {
        log.info("更新redis中的所有文章标签和对应的文章数————开始");
        List<BlogTagDto> blogTagAndNum = tBlogService.getBlogTagAndNum();
        redisUtil.set("blogTagAndNum", blogTagAndNum);
        log.info("更新redis中的所有文章标签和对应的文章数————结束");
    }

    /**
     *4、获得热门类别和对应的文章数
     *
     * @return
     */
    @RequestMapping("/getHotBlogTypeAndNum")
    public ResponseResult getHotBlogTypeAndNum() {
        if (redisUtil.get("hotBlogTypeAndNum") == null) {
            updateHotBlogTypeAndNum();
        }
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获得热门类别和对应的文章数成功",redisUtil.get("blogTypeAndNum"));
    }

    /**
     * 4、更新redis中的获得热门类别和对应的文章数
     *
     * @return
     */
    @RequestMapping("/updateHotBlogTypeAndNum")
    public void updateHotBlogTypeAndNum() {
        log.info("更新redis中的热门类别和对应的文章数————开始");
        List<PageData> blogTagAndNum = tBlogService.getHotBlogTypeAndNum();
        redisUtil.set("hotBlogTypeAndNum", blogTagAndNum);
        log.info("更新redis中的热门类别和对应的文章数————结束");
    }

    /**
     *5、获得操作日志列表数据
     *
     * @return
     */
    @RequestMapping("/getOperLogList")
    public ResponseResult getOperLogList() {
        List<PageData> operationLogs1 = sysOperLogService.getOperationLogs();
        List<List<String>> operationLogList=new ArrayList<>();

        for (PageData pageData : operationLogs1) {
            List<String> operationLogs=new ArrayList<>();
            operationLogs.add(pageData.get("operName")+"");
            operationLogs.add(pageData.get("title")+"");
            operationLogs.add(pageData.get("operIp")+"");
            operationLogs.add(pageData.get("operAddr")+"");
            if((pageData.get("status")+"").equals("0")){
                operationLogs.add("成功");
            }else{
                operationLogs.add("异常");
            }
            operationLogs.add(pageData.get("operTime")+"");
            operationLogList.add(operationLogs);
        }
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获得操作日志列表数据成功",operationLogList);
    }

    /**
     *6、获得最受欢迎十大作者和对应的文章数
     *
     * @return
     */
    @RequestMapping("/getHotAuthorAndNum")
    public ResponseResult getHotAuthorAndNum() {
        if (redisUtil.get("hotAuthorAndNum") == null) {
            updateHotAuthorAndNum();
        }
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获得最受欢迎十大作者和对应的文章数成功",redisUtil.get("hotAuthorAndNum"));
    }

    /**
     * 6、更新redis中的最受欢迎十大作者和对应的文章数
     *
     * @return
     */
    @RequestMapping("/updateHotAuthorAndNum")
    public void updateHotAuthorAndNum() {
        log.info("更新redis中的热门类别和对应的文章数————开始");
        List<PageData> blogTagAndNum = tBlogService.getHotAuthorAndNum();
        redisUtil.set("hotAuthorAndNum", blogTagAndNum);
        log.info("更新redis中的热门类别和对应的文章数————结束");
    }

    /**
     *7、获得各个省份中的各个访问量
     *
     * @return
     */
    @RequestMapping("/getProvinceAndNum")
    public ResponseResult getProvinceAndNum() {
        if (redisUtil.get("provinceAndNum") == null) {
            updateProvinceAndNum();
        }
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获得各个省份中的各个访问量成功",redisUtil.get("provinceAndNum"));
    }

    /**
     * 7、更新redis中的各个省份中的各个访问量
     *
     * @return
     */
    @RequestMapping("/updateProvinceAndNum")
    public void updateProvinceAndNum() {
        log.info("更新redis中的各个省份中的各个访问量————开始");
        List<PageData> provinceAndNum = sysOperLogService.getProvinceAndNum();
        redisUtil.set("provinceAndNum", provinceAndNum);
        log.info("更新redis中的各个省份中的各个访问量————结束");
    }
}
