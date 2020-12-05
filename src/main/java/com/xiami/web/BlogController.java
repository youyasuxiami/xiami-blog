package com.xiami.web;

import com.xiami.base.ResponseResult;
import com.xiami.dto.BlogDto;
import com.xiami.dto.BlogQueryDto;
import com.xiami.service.TBlogService;
import com.xiami.utils.JWTUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @PutMapping("/changeRecommend")
    public ResponseResult changeRecommend(Integer id, Integer recommend) {
        return tBlogService.changeRecommend(id, recommend);
    }

    @PutMapping("/changeShareStatement")
    public ResponseResult changeShareStatement(Integer id, Integer shareStatement) {
        return tBlogService.changeShareStatement(id, shareStatement);
    }

    @PutMapping("/changeAppreciation")
    public ResponseResult changeAppreciation(Integer id, Integer appreciation) {
        return tBlogService.changeAppreciation(id, appreciation);
    }

    @PutMapping("/changeCommentabled")
    public ResponseResult changeCommentabled(Integer id, Integer commentabled) {
        return tBlogService.changeCommentabled(id, commentabled);
    }

    @DeleteMapping("/deleteBlog")
    public ResponseResult deleteBlog(Integer id) {
        return tBlogService.deleteBlog(id);
    }

    @DeleteMapping("/deleteBlogs")
    public ResponseResult deleteBlogs(Integer[] ids) {
        return tBlogService.deleteBlogs(ids);
    }


}
