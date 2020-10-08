package com.xiami.dao;

import com.xiami.entity.TComment;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TCommentMapper extends MyMapper<TComment> {

    List<TComment> getCommentList(Integer blogId);

    List<TComment> getReplyList(Integer toCommentId);

    int updateByTCommentIds(List<Integer> ids);

}