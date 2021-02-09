package com.xiami.service;

import com.xiami.base.ResponseResult;
import com.xiami.dto.CommentDto;
import com.xiami.dto.TcommentQueryDto;
import com.xiami.dto.TcommentReportQueryDto;
import com.xiami.entity.TComment;
import com.xiami.entity.TCommentReport;

public interface TCommentService {


    ResponseResult addComment(CommentDto commentDto);

    /**
     * 获得评论列表
     * @param tCommentQueryDto
     * @return
     */
    ResponseResult getComments(TcommentQueryDto tCommentQueryDto);

    /**
     * 更新用户状态
     * @param tComment
     * @return
     */
    ResponseResult updateCommentStatus(TComment tComment);

    /**
     * 获得举报信息列表
     * @param tCommentQueryDto
     * @return
     */
    ResponseResult getCommentReportList(TcommentReportQueryDto tCommentQueryDto);

    /**
     * 更新用户状态
     * @param tCommentReport
     * @return
     */
    ResponseResult updateProgressStatusName(TCommentReport tCommentReport);


}

