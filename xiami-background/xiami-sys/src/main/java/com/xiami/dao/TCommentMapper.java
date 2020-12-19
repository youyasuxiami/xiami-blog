package com.xiami.dao;

import com.xiami.dto.TcommentQueryDto;
import com.xiami.entity.TComment;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TCommentMapper extends MyMapper<TComment> {

    List<TComment> getCommentList(Integer blogId);

    List<TComment> getReplyList(Integer toCommentId);

    int updateByTCommentIds(List<Integer> ids);

    /**
     * 根据搜索条件获得评论劫镖
     * @param tCommentQueryDto
     * @return
     */
    List<TComment> selectBySearch(TcommentQueryDto tCommentQueryDto);

}