package com.xiami.service;

import com.xiami.base.ResponseResult;
import com.xiami.dto.TagQueryDto;
import com.xiami.entity.TTag;

public interface TTagService{

    ResponseResult getTags();

    ResponseResult getTagList(TagQueryDto tagQueryDto);

    ResponseResult addTag(TTag tTag);

    ResponseResult updateTag(TTag tTag);

    ResponseResult deleteTag(Integer id);

    ResponseResult deleteTags(Integer[] ids);

    /**
     * 获取该博客拥有的所有标签
     * @return
     */
    ResponseResult getCheckedTags(Integer id);
}
