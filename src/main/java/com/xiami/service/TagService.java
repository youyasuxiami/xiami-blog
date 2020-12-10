package com.xiami.service;

import com.xiami.dto.front.BlogSortListDto;

import java.util.List;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­10 22:50
 */
public interface TagService {
    List<String> getTagType();

    List<BlogSortListDto> getArticleByBlogTag(String tagName);
}
