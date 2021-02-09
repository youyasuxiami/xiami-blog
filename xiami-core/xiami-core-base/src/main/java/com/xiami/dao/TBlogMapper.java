package com.xiami.dao;

import com.xiami.dto.BlogQueryDto;
import com.xiami.dto.BlogTagDto;
import com.xiami.dto.BlogTypeDto;
import com.xiami.dto.HotBlogTypeDto;
import com.xiami.entity.PageData;
import com.xiami.entity.TBlog;
import tk.mybatis.mapper.MyMapper;

import java.util.Date;
import java.util.List;

public interface TBlogMapper extends MyMapper<TBlog> {

    List<TBlog> selectBySearch(BlogQueryDto blogQueryDto);

    int deleteByIds(Integer[] ids);

    List<TBlog> getArticleByMonth(Date monthDate);

    List<TBlog> getArticleByBlogType(String blogType);

    List<TBlog> getArticleByBlogTag(String tagName);

    List<PageData> getBlogTypeAndNum();

    List<BlogTagDto> getBlogTagAndNum();

    //List<HotBlogTypeDto> getHotBlogTypeAndNum();

    List<PageData> getHotBlogTypeAndNum();

    List<PageData> getHotBlogTypeNameAndNum(List<String>typeName);

    List<PageData> getHotAuthor();

    List<PageData> getHotAuthorAndNum(List<Integer> hotUserIds);
}