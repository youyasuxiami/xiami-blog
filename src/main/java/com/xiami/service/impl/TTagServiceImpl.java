package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.TBlogTagsMapper;
import com.xiami.dao.TTagMapper;
import com.xiami.dto.TagQueryDto;
import com.xiami.entity.TBlogTags;
import com.xiami.entity.TTag;
import com.xiami.service.TTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TTagServiceImpl implements TTagService{

    @Resource
    private TTagMapper tTagMapper;

    @Resource
    private TBlogTagsMapper tBlogTagsMapper;

    @Override
    public ResponseResult getTags() {
        List<TTag> tTags = tTagMapper.selectAll();
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获取所有的博客标签数据成功",tTags);
    }

    /**
     * 获得分页数据
     * @param tagQueryDto
     * @return
     */
    @Override
    public ResponseResult getTagList(TagQueryDto tagQueryDto) {
        PageHelper.startPage(tagQueryDto.getPageNum(),tagQueryDto.getPageSize());
        List<TTag> tTags = tTagMapper.selectBySearch(tagQueryDto);
        PageInfo pageInfo = new PageInfo(tTags);
        long total = pageInfo.getTotal();
        PageResult pageResult=new PageResult(total,tTags);
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获得类别分页数据",pageResult);
    }

    @Override
    public ResponseResult addTag(TTag tTag) {
        try {
            int insert = tTagMapper.insertSelective(tTag);
            if(insert>0){
                return new ResponseResult(ResponseResult.CodeStatus.OK,"新增标签成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL,"新增标签失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL,"新增标签失败");
        }
    }

    @Override
    public ResponseResult updateTag(TTag tTag) {
        try {
            tTag.setUpdateTime(new Date());
            int insert = tTagMapper.updateByPrimaryKeySelective(tTag);
            if(insert>0){
                return new ResponseResult(ResponseResult.CodeStatus.OK,"更新标签成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL,"更新标签失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL,"更新标签失败");
        }
    }

    @Override
    public ResponseResult deleteTag(Integer id) {
        try {
            int i = tTagMapper.deleteByPrimaryKey(id);
            if(i>0){
                return new ResponseResult(ResponseResult.CodeStatus.OK,"删除标签成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.OK,"删除标签失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL,"删除标签失败");
        }
    }

    @Override
    public ResponseResult deleteTags(Integer[] ids) {
        try {
            int i = tTagMapper.deleteByIds(ids);
            if(i>0){
                return new ResponseResult(ResponseResult.CodeStatus.OK,"批量删除标签成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.OK,"批量删除标签失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL,"批量删除标签失败");
        }
    }

    @Override
    public ResponseResult getCheckedTags(Integer id) {
        TBlogTags tBlogTags = new TBlogTags();
        tBlogTags.setBlogsId(id);
        //获取该用户的所有角色id
        List<Integer> select = tBlogTagsMapper.select(tBlogTags)
                .stream()
                .map(TBlogTags::getTagsId)
                .collect(Collectors.toList());
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取该博客的所有标签信息成功", select);
    }
}

