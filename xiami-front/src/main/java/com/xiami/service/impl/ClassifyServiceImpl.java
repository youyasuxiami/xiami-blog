package com.xiami.service.impl;

import com.xiami.dao.TBlogMapper;
import com.xiami.dao.TTagMapper;
import com.xiami.dao.TTypeMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.BlogSortListDto;
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
import java.util.ArrayList;
import java.util.List;


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

    public List<BlogSortListDto> getBlogSortListDtos(List<TBlog> tBlogs) {
        List<BlogSortListDto> blogSortListDtos = new ArrayList<>();

        for (TBlog tBlog : tBlogs) {
            BlogSortListDto blogSortListDto = new BlogSortListDto();
            BeanUtils.copyProperties(tBlog, blogSortListDto);

            //翻译用户名
            User user = new User();
            user.setId(tBlog.getUserId());
            String userName = userMapper.selectOne(user).getName();
            blogSortListDto.setUserName(userName);

            //翻译分类名
            TType tType = new TType();
            tType.setId(tBlog.getTypeId());
            String typeName = tTypeMapper.selectOne(tType).getName();
            blogSortListDto.setTypeName(typeName);

            //翻译推荐等级
            String recommendValue = DictionaryUtils.toChinese("recommend_type", tBlog.getRecommend() + "");
            blogSortListDto.setRecommend(recommendValue);

            //翻译发布还是草稿状态
            if (tBlog.getPublished() == 1) {
                blogSortListDto.setPublish("发布");
            } else if (tBlog.getPublished() == 2) {
                blogSortListDto.setPublish("已保存");
            }

            //翻译原创
            if ("1".equals(tBlog.getFlag())) {
                blogSortListDto.setFlag("原创");
            } else if ("2".equals(tBlog.getFlag())) {
                blogSortListDto.setFlag("转载声明");
            } else if ("3".equals(tBlog.getFlag())) {
                blogSortListDto.setFlag("翻译");
            }

            //翻译标签集合
            List<TTag> tTags = tTagMapper.selectByBlogId(tBlog.getId());
            blogSortListDto.setTagList(tTags);
            blogSortListDtos.add(blogSortListDto);
        }
        return blogSortListDtos;
    }
}

