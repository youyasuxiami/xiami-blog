package com.xiami.controller;

import com.xiami.annotation.OperatorLog;
import com.xiami.base.ResponseResult;
import com.xiami.dto.BlogDto;
import com.xiami.dto.TcommentQueryDto;
import com.xiami.dto.TcommentReportQueryDto;
import com.xiami.entity.TComment;
import com.xiami.entity.TCommentReport;
import com.xiami.entity.User;
import com.xiami.service.TBlogService;
import com.xiami.service.TCommentService;
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
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private TBlogService tBlogService;

    @Resource
    private TCommentService tCommentService;

    @GetMapping("/getComments")
    public ResponseResult getBlogs(TcommentQueryDto tCommentQueryDto) {
        return tCommentService.getComments(tCommentQueryDto);
    }

    /**
     * 更新评论状态
     * @param id
     * @param status
     * @return
     */
    @OperatorLog("更新评论状态")
    @PostMapping("/updateCommentStatus")
    public ResponseResult<User> updateCommentStatus(Integer id, String status) {
        TComment comment=new TComment();
        comment.setId(id);
        comment.setStatus(status);
        return tCommentService.updateCommentStatus(comment);
    }

    /**
     * 获得举报列表
     * @param tCommentQueryDto
     * @return
     */
    @GetMapping("/getCommentReportList")
    public ResponseResult getCommentReportList(TcommentReportQueryDto tCommentQueryDto) {
        return tCommentService.getCommentReportList(tCommentQueryDto);
    }

    /**
     * 更新进展状态
     * @param id
     * @param status
     * @return
     */
    @OperatorLog("更新进展状态")
    @PostMapping("/updateProgressStatusName")
    public ResponseResult updateProgressStatusName(Integer id, String status) {
        TCommentReport tCommentReport=new TCommentReport();
        tCommentReport.setId(id);
        tCommentReport.setProgressStatus(Integer.valueOf(status));
        return tCommentService.updateProgressStatusName(tCommentReport);
    }
}
