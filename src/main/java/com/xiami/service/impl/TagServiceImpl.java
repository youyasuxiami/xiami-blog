package com.xiami.service.impl;

import com.xiami.dao.TBlogMapper;
import com.xiami.dao.TTagMapper;
import com.xiami.dao.TTypeMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.front.BlogSortListDto;
import com.xiami.entity.TBlog;
import com.xiami.service.ClassifyService;
import com.xiami.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.xiami.utils.DictionaryUtils.getBlogSortListDtos;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­10 22:51
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TBlogMapper tBlogMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TTypeMapper tTypeMapper;

    @Resource
    private TTagMapper tTagMapper;


    public List<String> getTagType() {
        List<String> list = tTagMapper.getAllTagName();
        return list;
    }

    @Override
    public List<BlogSortListDto> getArticleByBlogTag(String tagName) {
        List<TBlog> tBlogs = tBlogMapper.getArticleByBlogTag(tagName);

        List<BlogSortListDto> blogSortListDtos = getBlogSortListDtos(tBlogs);
        return blogSortListDtos;
    }
}

