package com.xiami.web.front;

import com.xiami.base.ResponseResult;
import com.xiami.dto.BlogListDto;
import com.xiami.dto.front.BlogSortListDto;
import com.xiami.entity.TBlog;
import com.xiami.service.AboutService;
import com.xiami.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­30 20:38
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/sort")
public class SortController {

    @Autowired
    private SortService sortService;

    /**
     * 获得归档的所有时间，格式是2020年03月
     * @return
     */
    @RequestMapping("/getSortTimes")
    public ResponseResult getSortTimes() {
        Set<String> blogs = sortService.getSortTimes();
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获取归档时间的数据成功",blogs);
    }

    /**
     * 根据时间获取博客数据
     * @return
     */
    @RequestMapping("/getArticleByMonth")
    public ResponseResult getArticleByMonth(String monthDate) {
        List<BlogSortListDto> blogs = sortService.getArticleByMonth(monthDate);
        return new ResponseResult(ResponseResult.CodeStatus.OK,"根据时间获取博客数据",blogs);
    }
}
