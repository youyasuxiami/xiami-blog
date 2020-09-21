package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.FrontPageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.TBlogMapper;
import com.xiami.dao.TBlogTagsMapper;
import com.xiami.dao.TTagMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.IndexBlogListDto;
import com.xiami.entity.TBlog;
import com.xiami.entity.TBlogTags;
import com.xiami.entity.TTag;
import com.xiami.entity.User;
import com.xiami.service.HomeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­30 23:05
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private TBlogMapper tBlogMapper;

    @Autowired
    private TBlogTagsMapper tBlogTagsMapper;

    @Autowired
    private TTagMapper tTagMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult getBlogs(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        List<TBlog> tBlogs = tBlogMapper.selectAll();
        List<IndexBlogListDto> indexBlogListDtos = new ArrayList<>();
        for (TBlog tBlog : tBlogs) {
            IndexBlogListDto indexBlogListDto = new IndexBlogListDto();
            BeanUtils.copyProperties(tBlog, indexBlogListDto);

            //翻译用户名
            User user = new User();
            user.setId(tBlog.getUserId());
            String userName = userMapper.selectOne(user).getName();
            indexBlogListDto.setUserName(userName);

            //获取该博客的所有标签
            TBlogTags tBlogTags=new TBlogTags();
            tBlogTags.setBlogsId(tBlog.getId());
            //获得所有的标签id
            List<Integer> collect = tBlogTagsMapper.select(tBlogTags).stream().map(TBlogTags::getTagsId).collect(Collectors.toList());
            List<TTag> tTags = tTagMapper.selectByTagIds(collect);
            indexBlogListDto.setTTags(tTags);//存放list的tag

            indexBlogListDtos.add(indexBlogListDto);
        }

        PageInfo<TBlog> info = new PageInfo<>(tBlogs);
        long total = info.getTotal();
        FrontPageResult pageResult = new FrontPageResult(total, indexBlogListDtos,currentPage,pageSize);
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获取数据成功",pageResult);
    }

    @Override
    public ResponseResult getHotTags() {
        //获得标签中文名
        List<TTag> tTags = tTagMapper.selectAll();
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获取标签数据成功",tTags);
    }

    @Override
    public ResponseResult getBlogByLevel(int level) {
        List<TBlog> tBlogs = tBlogMapper.selectAll();
        if(level==3){
            tBlogs = tBlogs.stream().limit(3).collect(Collectors.toList());
        }
        if(level==4){
            tBlogs = tBlogs.stream().limit(5).collect(Collectors.toList());
        }
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获取数据成功",tBlogs);
    }

    @Override
    public ResponseResult getHotBlog() {
        List<TBlog> tBlogs = tBlogMapper.selectAll().stream()
                .sorted(Comparator.comparing(TBlog::getViews).reversed())
                .limit(5)
                .collect(Collectors.toList());
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获取数据成功",tBlogs);
    }
}
