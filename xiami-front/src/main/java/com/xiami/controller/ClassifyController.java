package com.xiami.controller;

import com.xiami.base.ResponseResult;
import com.xiami.dto.BlogSortListDto;
import com.xiami.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­30 20:38
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/classify")
public class ClassifyController {

    @Autowired
    private ClassifyService classifyService;

    /**
     * 获得所有现有博客的分类
     * @return
     */
    @RequestMapping("/getBlogTypes")
    public ResponseResult getBlogTypes() {
        List<String> blogTypes = classifyService.getBlogTypes();
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获得所有的博客分类数据",blogTypes);
    }

    /**
     * 根据博客分类获取博客数据
     * @return
     */
    @RequestMapping("/getArticleByBlogType")
    public ResponseResult getArticleByBlogType(String blogTypeName) {
        List<BlogSortListDto> blogs = classifyService.getArticleByBlogType(blogTypeName);
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获得选中分类获取博客数据成功",blogs);
    }
}
