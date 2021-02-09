package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.TBlogMapper;
import com.xiami.dao.TBlogTagsMapper;
import com.xiami.dao.TTypeMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.BlogDto;
import com.xiami.dto.BlogListDto;
import com.xiami.dto.BlogQueryDto;
import com.xiami.dto.BlogTagDto;
import com.xiami.dto.BlogTypeDto;
import com.xiami.dto.HotBlogTypeDto;
import com.xiami.entity.PageData;
import com.xiami.entity.TBlog;
import com.xiami.entity.TBlogTags;
import com.xiami.entity.User;
import com.xiami.service.TBlogService;
import com.xiami.utils.DictionaryUtils;
import com.xiami.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.xiami.utils.DictionaryUtils.toBlogListDtos;


@Service
public class TBlogServiceImpl implements TBlogService {

    @Resource
    private TBlogMapper tBlogMapper;

    @Resource
    private TBlogTagsMapper tBlogTagsMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TTypeMapper tTypeMapper;

    @Resource
    private DictionaryUtils dictionaryUtils;

    /**
     * 获得所有的分类
     *
     * @return
     */
    @Override
    public ResponseResult getBlogTypes() {
        List<TBlog> tBlogs = tBlogMapper.selectAll();
        return null;
    }

    @Transactional
    @Override
    public ResponseResult addBlog(BlogDto blogDto) {
        User user = UserUtils.getUser();

        TBlog tBlog = new TBlog();
        BeanUtils.copyProperties(blogDto, tBlog);
        tBlog.setPublished(1);
        tBlog.setUserId(user.getId());//作者id
        tBlog.setShareStatement(BooleanToInteger(blogDto.getShareStatement()));//转载声明
        tBlog.setAppreciation(BooleanToInteger(blogDto.getAppreciation()));//赞赏
        tBlog.setCommentabled(BooleanToInteger(blogDto.getCommentabled()));//评论
        tBlog.setCreateTime(new Date());
        tBlog.setUpdateTime(new Date());
        tBlog.setViews(0);
        tBlog.setCollectCount(0);

        try {
            int i = tBlogMapper.insertUseGeneratedKeys(tBlog);

            TBlogTags tBlogTags = new TBlogTags();
            tBlogTags.setBlogsId(tBlog.getId());

            //插入标签-博客表
            List<TBlogTags> tBlogTagsList = new ArrayList<>();
            Integer[] tagIds = blogDto.getTagIds();
            List<Integer> tagIdList = Arrays.asList(tagIds);
            for (Integer integer : tagIdList) {
                TBlogTags tBlogTags1 = new TBlogTags();
                tBlogTags1.setTagsId(integer);
                tBlogTags1.setBlogsId(tBlog.getId());//设置新增的博客的id
                tBlogTagsList.add(tBlogTags1);
            }
            int insert = tBlogTagsMapper.insertList(tBlogTagsList);

            if (i > 0 && insert > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "新增博客成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "新增博客失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "新增博客失败");
        }
    }

    /**
     * 编辑博客
     * @param blogDto
     * @return
     */
    @Transactional
    @Override
    public ResponseResult updateBlog(BlogDto blogDto) {

        TBlog tBlog = new TBlog();
        BeanUtils.copyProperties(blogDto, tBlog);
        tBlog.setPublished(1);
        tBlog.setShareStatement(BooleanToInteger(blogDto.getShareStatement()));//转载声明
        tBlog.setAppreciation(BooleanToInteger(blogDto.getAppreciation()));//赞赏
        tBlog.setCommentabled(BooleanToInteger(blogDto.getCommentabled()));//评论
        tBlog.setUpdateTime(new Date());
        tBlog.setViews(0);
        tBlog.setCollectCount(0);

        try {
            int i = tBlogMapper.updateByPrimaryKeySelective(tBlog);

            TBlogTags tBlogTags = new TBlogTags();
            tBlogTags.setBlogsId(tBlog.getId());
            //根据主键删除
            int i1 = tBlogTagsMapper.delete(tBlogTags);

            //插入标签-博客表
            List<TBlogTags> tBlogTagsList = new ArrayList<>();
            Integer[] tagIds = blogDto.getTagIds();
            List<Integer> tagIdList = Arrays.asList(tagIds);
            for (Integer integer : tagIdList) {
                TBlogTags tBlogTags1 = new TBlogTags();
                tBlogTags1.setTagsId(integer);
                tBlogTags1.setBlogsId(tBlog.getId());//设置新增的博客的id
                tBlogTagsList.add(tBlogTags1);
            }
            int insert = tBlogTagsMapper.insertList(tBlogTagsList);

            if (i > 0 && insert > 0 && i1>0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "编辑博客成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "编辑博客失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "编辑博客失败");
        }
    }


    public Integer BooleanToInteger(Boolean b) {
        if (b) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public ResponseResult getBlogs(BlogQueryDto blogQueryDto) {
        //第一个参数是当前页
        PageHelper.startPage(blogQueryDto.getPageNum(), blogQueryDto.getPageSize());
        List<TBlog> tBlogs = tBlogMapper.selectBySearch(blogQueryDto);

        List<BlogListDto> blogListDtos = toBlogListDtos(tBlogs);

        PageInfo<TBlog> info = new PageInfo<>(tBlogs);
        long total = info.getTotal();
        PageResult pageResult = new PageResult(total, blogListDtos);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取博客分页数据成功", pageResult);
    }



    @Override
    public ResponseResult changeRecommend(Integer id, Integer recommend) {
        String msg = "";
        TBlog tBlog = new TBlog();
        tBlog.setId(id);
        tBlog.setRecommend(recommend);
        if (recommend == 1) {
            msg = "开启推荐";
        } else {
            msg = "关闭推荐";
        }
        return getResponseResult(msg, tBlog);
    }

    @Override
    public ResponseResult changeShareStatement(Integer id, Integer shareStatement) {
        String msg = "";
        TBlog tBlog = new TBlog();
        tBlog.setId(id);
        tBlog.setShareStatement(shareStatement);
        if (shareStatement == 1) {
            msg = "开启可转载";
        } else {
            msg = "关闭可转载";
        }
        return getResponseResult(msg, tBlog);
    }

    @Override
    public ResponseResult changeAppreciation(Integer id, Integer appreciation) {
        String msg = "";
        TBlog tBlog = new TBlog();
        tBlog.setId(id);
        tBlog.setAppreciation(appreciation);
        if (appreciation == 1) {
            msg = "开启赞赏";
        } else {
            msg = "关闭赞赏";
        }
        return getResponseResult(msg, tBlog);
    }

    @Override
    public ResponseResult changeCommentabled(Integer id, Integer commentabled) {
        String msg = "";
        TBlog tBlog = new TBlog();
        tBlog.setId(id);
        tBlog.setCommentabled(commentabled);
        if (commentabled == 1) {
            msg = "开启可评论";
        } else {
            msg = "关闭可评论";
        }
        return getResponseResult(msg, tBlog);
    }

    private ResponseResult getResponseResult(String msg, TBlog tBlog) {
        try {
            int i = tBlogMapper.updateByPrimaryKeySelective(tBlog);
            if (i > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, msg + "成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, msg + "失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, msg + "失败");
        }
    }


    @Override
    public ResponseResult deleteBlog(Integer id) {
        try {
            int i = tBlogMapper.deleteByPrimaryKey(id);
            if (i > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "删除博客成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.OK, "删除博客失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "删除博客失败");
        }
    }

    @Override
    public ResponseResult deleteBlogs(Integer[] ids) {
        try {
            int i = tBlogMapper.deleteByIds(ids);
            if (i > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "批量删除博客成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.OK, "批量删除博客失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "批量删除博客失败");
        }
    }

    @Override
    public List<PageData> getBlogTypeAndNum() {
        List<PageData> blogTypeAndNum = tBlogMapper.getBlogTypeAndNum();
        return blogTypeAndNum;
    }

    @Override
    public List<BlogTagDto> getBlogTagAndNum() {
        List<BlogTagDto> blogTagAndNum = tBlogMapper.getBlogTagAndNum();
        return blogTagAndNum;
    }

    @Override
    public List<PageData> getHotBlogTypeAndNum() {
        List<PageData> hotBlogType = tBlogMapper.getHotBlogTypeAndNum();
        List<String> typeName=new ArrayList<>();
        for (PageData pageData : hotBlogType) {
            typeName.add(pageData.getString("name"));
        }
        List<PageData> hotBlogTypeNameAndNum = tBlogMapper.getHotBlogTypeNameAndNum(typeName);

        return hotBlogTypeNameAndNum;
    }

    @Override
    public List<PageData> getHotAuthorAndNum() {
        List<PageData> hotAuthor = tBlogMapper.getHotAuthor();
        List<Integer> hotUserIds=new ArrayList<>();
        for (PageData pageData : hotAuthor) {
            hotUserIds.add((Integer)pageData.get("user_id"));
        }
        List<PageData> hotAuthorAndNum = tBlogMapper.getHotAuthorAndNum(hotUserIds);

        return hotAuthorAndNum;
    }
}

