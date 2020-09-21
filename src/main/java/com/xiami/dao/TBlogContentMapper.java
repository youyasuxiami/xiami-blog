package com.xiami.dao;

import com.xiami.dto.BlogContentDto;
import com.xiami.entity.TBlog;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TBlogContentMapper extends MyMapper<TBlog> {

    BlogContentDto selectById(Integer id);

    List<TBlog> getBlogsByTagIds(List tagIds);
}