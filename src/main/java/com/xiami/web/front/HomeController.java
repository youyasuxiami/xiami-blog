package com.xiami.web.front;

import com.xiami.base.ResponseResult;
import com.xiami.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­30 20:38
 */
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
}
