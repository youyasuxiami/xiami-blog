package com.xiami.controller;

import com.xiami.annotation.OperatorLog;
import com.xiami.base.ResponseResult;
import com.xiami.dto.BlogDto;
import com.xiami.dto.BlogQueryDto;
import com.xiami.service.TBlogService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private TBlogService tBlogService;

    @GetMapping("/getBlogTypes")
    public ResponseResult getBlogTypes() {
        return tBlogService.getBlogTypes();
    }

    @OperatorLog("新增标签")
    @PostMapping("/addBlog")
    public ResponseResult addTag(@RequestBody BlogDto blogDto) {
        //新增博客
        if (blogDto.getId() == null) {
            return tBlogService.addBlog(blogDto);
        }
        //编辑博客
        return tBlogService.updateBlog(blogDto);
    }

    @GetMapping("/getBlogs")
    public ResponseResult getBlogs(BlogQueryDto blogQueryDto) {
        return tBlogService.getBlogs(blogQueryDto);
    }

    @OperatorLog("修改是否推荐")
    @PutMapping("/changeRecommend")
    public ResponseResult changeRecommend(Integer id, Integer recommend) {
        return tBlogService.changeRecommend(id, recommend);
    }

    @OperatorLog("修改是否可转载")
    @PutMapping("/changeShareStatement")
    public ResponseResult changeShareStatement(Integer id, Integer shareStatement) {
        return tBlogService.changeShareStatement(id, shareStatement);
    }

    @OperatorLog("修改是否可赞赏")
    @PutMapping("/changeAppreciation")
    public ResponseResult changeAppreciation(Integer id, Integer appreciation) {
        return tBlogService.changeAppreciation(id, appreciation);
    }

    @OperatorLog("修改是否可评论")
    @PutMapping("/changeCommentabled")
    public ResponseResult changeCommentabled(Integer id, Integer commentabled) {
        return tBlogService.changeCommentabled(id, commentabled);
    }

    @OperatorLog("删除博客")
    @DeleteMapping("/deleteBlog")
    public ResponseResult deleteBlog(Integer id) {
        return tBlogService.deleteBlog(id);
    }

    @OperatorLog("批量删除博客")
    @DeleteMapping("/deleteBlogs")
    public ResponseResult deleteBlogs(Integer[] ids) {
        return tBlogService.deleteBlogs(ids);
    }


}
