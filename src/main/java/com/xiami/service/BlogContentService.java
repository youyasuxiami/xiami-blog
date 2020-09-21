package com.xiami.service;

import com.xiami.base.ResponseResult;
import com.xiami.dto.CommentDeteleDto;
import com.xiami.dto.CommentDto;
import com.xiami.dto.CommentReportDto;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­09­12 17:30
 */
public interface BlogContentService {

    ResponseResult getBlogById(Integer id);

    ResponseResult praiseBlogById(Integer id);

    ResponseResult getSameBlogByBlogId(Integer id);

    ResponseResult addComment(CommentDto commentDto);

    ResponseResult getCommentList(CommentDto commentDto);

    ResponseResult reportComment(CommentReportDto commentReportDto);

    ResponseResult deleteComment(CommentDeteleDto commentDeteleDto);
}
