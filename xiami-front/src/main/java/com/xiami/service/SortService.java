package com.xiami.service;



import com.xiami.dto.BlogSortListDto;

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
