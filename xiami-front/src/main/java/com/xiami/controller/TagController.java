package com.xiami.controller;

import com.xiami.annotation.OperatorLog;
import com.xiami.base.ResponseResult;
import com.xiami.dto.BlogSortListDto;
import com.xiami.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­30 20:38
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 获得所有现有博客的标签
     * @return
     */
    @RequestMapping("/getTagType")
    public ResponseResult getTagType() {
        List<String> blogTypes = tagService.getTagType();
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获得所有的博客标签数据",blogTypes);
    }

    /**
     * 根据博客分类获取博客数据
     * @return
     */
    @RequestMapping("/getArticleByBlogTag")
    public ResponseResult getArticleByBlogTag(String tagName) {
        List<BlogSortListDto> blogs = tagService.getArticleByBlogTag(tagName);
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获得选中标签获取博客数据成功",blogs);
    }
}
