package com.xiami.service;

import com.xiami.base.ResponseResult;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­30 23:05
 */
public interface HomeService {
    ResponseResult getBlogs(int currentPage,int pageSize);

    ResponseResult getHotTags();

    ResponseResult getBlogByLevel(int level);

    ResponseResult getHotBlog();
}
