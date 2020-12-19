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
import com.xiami.service.SortService;
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

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­30 23:05
 */
@Service
public class SortServiceImpl implements SortService {

    @Autowired
    private TBlogMapper tBlogMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TTypeMapper tTypeMapper;

    @Resource
    private TTagMapper tTagMapper;



    public Set<String>  getSortTimes(){
        List<Date> collect = tBlogMapper.selectAll().stream()
                .map(TBlog::getCreateTime)
                .collect(Collectors.toList());
        Set<String> list=new TreeSet<>();
        for (Date date : collect) {
            String month = new SimpleDateFormat("yyyy年MM月").format(date);
            list.add(month);
        }
        return list;
    }

    @Override
    public List<BlogSortListDto> getArticleByMonth(String monthDate) {
        try {
            Date date=new SimpleDateFormat("yyyy年MM月").parse(monthDate);
            List<TBlog> tBlogs = tBlogMapper.getArticleByMonth(date);

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
                String recommendValue = DictionaryUtils.toChinese("recommend_type", tBlog.getRecommend()+"");
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
                List<TTag> tTags=tTagMapper.selectByBlogId(tBlog.getId());
                blogSortListDto.setTagList(tTags);
                blogSortListDtos.add(blogSortListDto);
            }
            return blogSortListDtos;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
