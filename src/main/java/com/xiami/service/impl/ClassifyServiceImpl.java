package com.xiami.service.impl;

import com.xiami.dao.TBlogMapper;
import com.xiami.dao.TTagMapper;
import com.xiami.dao.TTypeMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.front.BlogSortListDto;
import com.xiami.entity.TBlog;
import com.xiami.entity.TTag;
import com.xiami.entity.TType;
import com.xiami.entity.User;
import com.xiami.service.ClassifyService;
import com.xiami.utils.DictionaryUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.xiami.utils.DictionaryUtils.getBlogSortListDtos;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­10 22:51
 */
@Service
public class ClassifyServiceImpl implements ClassifyService {
    @Autowired
    private TBlogMapper tBlogMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TTypeMapper tTypeMapper;

    @Resource
    private TTagMapper tTagMapper;


    public List<String> getBlogTypes() {
        List<String> list = tTypeMapper.getAllTypeNames();
        return list;
    }

    @Override
    public List<BlogSortListDto> getArticleByBlogType(String blogTypeName) {
        List<TBlog> tBlogs = tBlogMapper.getArticleByBlogType(blogTypeName);

        List<BlogSortListDto> blogSortListDtos = getBlogSortListDtos(tBlogs);
        return blogSortListDtos;
    }
}

