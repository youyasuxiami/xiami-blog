package com.xiami.controller;

import com.xiami.annotation.OperatorLog;
import com.xiami.base.ResponseResult;
import com.xiami.dto.TagQueryDto;
import com.xiami.entity.TTag;
import com.xiami.service.TTagService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tag")
public class BlogTagController {

    @Resource
    private TTagService tTagService;

    @GetMapping("/getTags")
    public ResponseResult getTags() {
        return tTagService.getTags();
    }

    /**
     * 分页显示分类数据
     *
     * @param tagQueryDto
     * @return
     */
    @GetMapping("/getTagList")
    public ResponseResult getTypeList(TagQueryDto tagQueryDto) {
        return tTagService.getTagList(tagQueryDto);
    }

    /**
     * 新增/编辑
     *
     * @param tTag
     * @return
     */
    @OperatorLog("新增标签")
    @PostMapping("/addTag")
    public ResponseResult addTag(@RequestBody TTag tTag) {
        if (null == tTag.getId()) {
            //新增
            return tTagService.addTag(tTag);
        }
        //编辑
        return tTagService.updateTag(tTag);
    }

    @OperatorLog("删除标签")
    @DeleteMapping("/deleteTag")
    public ResponseResult deleteTag(Integer id) {
        return tTagService.deleteTag(id);
    }

    @OperatorLog("批量删除标签")
    @DeleteMapping("/deleteTags")
    public ResponseResult deleteTags(Integer[] ids) {
        return tTagService.deleteTags(ids);
    }

    /**
     * 获得一个博客所有的标签
     *
     * @return
     */
    @GetMapping("/getCheckedTags")
    public ResponseResult<TTag> getCheckedTags(Integer id) {
        return tTagService.getCheckedTags(id);
    }
}
