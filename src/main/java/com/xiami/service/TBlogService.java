package com.xiami.service;

import com.xiami.base.ResponseResult;
import com.xiami.dto.BlogDto;
import com.xiami.dto.BlogQueryDto;

public interface TBlogService {

    ResponseResult getBlogTypes();

    /**
     * 新增博客
     *
     * @param blogDto
     * @return
     */
    ResponseResult addBlog(BlogDto blogDto);

    /**
     * 编辑博客
     *
     * @param blogDto
     * @return
     */
    ResponseResult updateBlog(BlogDto blogDto);

    /**
     * 根据搜索框获得所有的博客分页数据
     *
     * @param blogQueryDto
     * @return
     */
    ResponseResult getBlogs(BlogQueryDto blogQueryDto);

    /**
     * 更改推荐状态
     *
     * @param id
     * @param recommend
     * @return
     */
    ResponseResult changeRecommend(Integer id, Integer recommend);

    ResponseResult changeShareStatement(Integer id, Integer shareStatement);

    ResponseResult changeAppreciation(Integer id, Integer appreciation);

    ResponseResult changeCommentabled(Integer id, Integer commentabled);

    ResponseResult deleteBlog(Integer id);

    ResponseResult deleteBlogs(Integer[] ids);
}
