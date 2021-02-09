package com.xiami.controller;

import com.xiami.annotation.OperatorLog;
import com.xiami.base.ResponseResult;
import com.xiami.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­30 20:38
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/index")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @RequestMapping("/getNewBlog")
    public ResponseResult getNewBlog(int currentPage, int pageSize) {
        ResponseResult blogs = homeService.getBlogs(currentPage, pageSize);
        return blogs;
    }

    @RequestMapping("/getHotTag")
    public ResponseResult getHotTag() {
        ResponseResult blogs = homeService.getHotTags();
        return blogs;
    }
    @RequestMapping("/getBlogByLevel")
    public ResponseResult getBlogByLevel(Integer level) {
        ResponseResult blogs = homeService.getBlogByLevel(level);
        return blogs;
    }

    @RequestMapping("/getHotBlog")
    public ResponseResult getHotBlog() {
        ResponseResult blogs = homeService.getHotBlog();
        return blogs;
    }
}
