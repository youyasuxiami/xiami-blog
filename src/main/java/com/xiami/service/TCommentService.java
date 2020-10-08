package com.xiami.service;

import com.xiami.base.ResponseResult;
import com.xiami.dto.CommentDto;

public interface TCommentService {


    ResponseResult addComment(CommentDto commentDto);
}

