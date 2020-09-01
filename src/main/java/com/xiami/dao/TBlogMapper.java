package com.xiami.dao;

import com.xiami.dto.BlogQueryDto;
import com.xiami.entity.TBlog;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TBlogMapper extends MyMapper<TBlog> {

    List<TBlog> selectBySearch(BlogQueryDto blogQueryDto);

    int deleteByIds(Integer[] ids);

}