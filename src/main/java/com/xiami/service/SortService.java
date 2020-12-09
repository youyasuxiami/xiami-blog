package com.xiami.service;

import com.xiami.base.ResponseResult;
import com.xiami.dto.BlogListDto;
import com.xiami.dto.front.BlogSortListDto;
import com.xiami.entity.TBlog;

import java.util.List;
import java.util.Set;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­30 23:05
 */
public interface SortService {
    Set<String> getSortTimes();

    List<BlogSortListDto> getArticleByMonth(String monthDate);

}
