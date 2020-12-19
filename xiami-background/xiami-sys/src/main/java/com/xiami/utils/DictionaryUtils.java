package com.xiami.utils;

import com.xiami.dao.SysDictionaryMapper;
import com.xiami.dao.TTagMapper;
import com.xiami.dao.TTypeMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.BlogListDto;
import com.xiami.entity.SysDictionary;
import com.xiami.entity.TBlog;
import com.xiami.entity.TTag;
import com.xiami.entity.TType;
import com.xiami.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：获取字典表的中文
 * 执行顺序：Constructor >> @Autowired >> @PostConstruct
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­30 18:32
 */
@Component
public class DictionaryUtils {

    private static DictionaryUtils dictionaryUtils;

    @Resource
    private SysDictionaryMapper sysDictionaryMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TTypeMapper tTypeMapper;

    @Resource
    private TTagMapper tTagMapper;



    /**
     * @PostConstruct注解的方法将会在依赖注入完成后被自动调用。
     */
    @PostConstruct
    public void init() {
        dictionaryUtils = this;
        dictionaryUtils.sysDictionaryMapper = this.sysDictionaryMapper;
        dictionaryUtils.userMapper = this.userMapper;
        dictionaryUtils.tTypeMapper = this.tTypeMapper;
        dictionaryUtils.tTagMapper = this.tTagMapper;
    }

    /**
     * 根据字典表的group的值：获取所有下拉框的值
     *
     * @param group
     * @return
     */
    public static List<SysDictionary> getDictionaryList(String group) {
        Example example = new Example(SysDictionary.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("group", group);
        List<SysDictionary> sysDictionaries = dictionaryUtils.sysDictionaryMapper.selectByExample(example);
        return sysDictionaries;
    }

    public static String toChinese(String group, String code) {
        Example example = new Example(SysDictionary.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("group", group);
        criteria.andEqualTo("code", code);
        List<SysDictionary> sysDictionaries = dictionaryUtils.sysDictionaryMapper.selectByExample(example);
        String s = sysDictionaries.get(0).getValue();
        //List<String> lists = sysDictionaries.stream().map(SysDictionary::getValue).collect(Collectors.toList());
        //String s = lists.get(0);
        return s;
    }

    public static List<BlogListDto> toBlogListDtos(List<TBlog> tBlogs) {
        List<BlogListDto> blogListDtos = new ArrayList<>();
        for (TBlog tBlog : tBlogs) {
            BlogListDto blogListDto = new BlogListDto();
            BeanUtils.copyProperties(tBlog, blogListDto);

            //翻译用户名
            User user = new User();
            user.setId(tBlog.getUserId());
            String userName = dictionaryUtils.userMapper.selectOne(user).getName();
            blogListDto.setUserName(userName);

            //翻译分类名
            TType tType = new TType();
            tType.setId(tBlog.getTypeId());
            String typeName = dictionaryUtils.tTypeMapper.selectOne(tType).getName();
            blogListDto.setTypeName(typeName);

            //翻译推荐等级
            String recommendValue = dictionaryUtils.toChinese("recommend_type", tBlog.getRecommend()+"");
            blogListDto.setRecommend(recommendValue);

            //翻译发布还是草稿状态
            if (tBlog.getPublished() == 1) {
                blogListDto.setPublish("发布");
            } else if (tBlog.getPublished() == 2) {
                blogListDto.setPublish("已保存");
            }

            //翻译原创
            if ("1".equals(tBlog.getFlag())) {
                blogListDto.setFlag("原创");
            } else if ("2".equals(tBlog.getFlag())) {
                blogListDto.setFlag("转载声明");
            } else if ("3".equals(tBlog.getFlag())) {
                blogListDto.setFlag("翻译");
            }
            blogListDtos.add(blogListDto);
        }
        return blogListDtos;
    }
}
