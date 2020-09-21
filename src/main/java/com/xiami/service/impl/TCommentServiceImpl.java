package com.xiami.service.impl;

import com.xiami.base.ResponseResult;
import com.xiami.dao.TCommentMapper;
import com.xiami.dto.CommentDto;
import com.xiami.entity.TComment;
import com.xiami.service.TCommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TCommentServiceImpl implements TCommentService {

    @Resource
    private TCommentMapper tCommentMapper;

    @Override
    public ResponseResult addComment(CommentDto commentDto) {
        TComment tComment = new TComment();
        BeanUtils.copyProperties(commentDto, tComment);
        try {
            int insert = tCommentMapper.insert(tComment);
            if (insert > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "插入评论成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "插入评论失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "插入评论失败");
        }
    }
}

