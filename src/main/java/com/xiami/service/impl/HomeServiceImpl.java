package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.FrontPageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.TBlogMapper;
import com.xiami.entity.TBlog;
import com.xiami.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public ResponseResult getBlogs(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        List<TBlog> tBlogs = tBlogMapper.selectAll();
        //翻译作者

        //根据博客文章的id获取所有的标签

        PageInfo<TBlog> info = new PageInfo<>(tBlogs);
        long total = info.getTotal();
        FrontPageResult pageResult = new FrontPageResult(total, tBlogs,currentPage,pageSize);
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获取数据成功",pageResult);
    }
}
