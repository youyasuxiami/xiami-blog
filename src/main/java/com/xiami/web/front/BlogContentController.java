package com.xiami.web.front;

import com.xiami.base.ResponseResult;
import com.xiami.dto.CommentDeteleDto;
import com.xiami.dto.CommentDto;
import com.xiami.dto.CommentReportDto;
import com.xiami.service.BlogContentService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date：2020­09­12 17:29
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/content")
public class BlogContentController  {

    @Autowired
    private BlogContentService blogContentService;

    /**
     * 根据博客id查询
     * 返回博客和标签信息
     * @param id
     * @return
     */
    @GetMapping("/getBlogById")
    public ResponseResult getBlogById(Integer id){
        return blogContentService.getBlogById(id);
    }

    /**
     * 获得点赞数
     * @param id
     * @return
     */
    @GetMapping("/praiseBlogById")
    public ResponseResult praiseBlogById(Integer id){
        return blogContentService.praiseBlogById(id);
    }

    /**
     * 通过博客id获取相关的博客文章（比如标签）
     * @param blogId
     * @return
     */
    @GetMapping("/getSameBlogByBlogId")
    public ResponseResult getSameBlogByBlogId(Integer blogId){
        return blogContentService.getSameBlogByBlogId(blogId);
    }

    @RequestMapping("/addComment")
    public ResponseResult addComment(@RequestBody CommentDto commentDto){
        return blogContentService.addComment(commentDto);
    }

    @RequestMapping("/getCommentList")
    public ResponseResult getCommentList(@RequestBody CommentDto commentDto){
        ResponseResult commentList = blogContentService.getCommentList(commentDto);
        return commentList;
    }

    /**
     * 举报功能
     * @param commentReportDto
     * @return
     */
    @PostMapping("/reportComment")
    public ResponseResult reportComment(@RequestBody CommentReportDto commentReportDto){
        ResponseResult commentList = blogContentService.reportComment(commentReportDto);
        return commentList;
    }
    /**
     * 删除功能
     * @param commentDeteleDto
     * @return
     */
    @DeleteMapping("/deleteComment")
    public ResponseResult deleteComment(@RequestBody CommentDeteleDto commentDeteleDto){
        ResponseResult commentList = blogContentService.deleteComment(commentDeteleDto);
        return commentList;
    }

}
