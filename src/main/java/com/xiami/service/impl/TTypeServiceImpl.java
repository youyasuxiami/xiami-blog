package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.TTypeMapper;
import com.xiami.dto.TypeQueryDto;
import com.xiami.entity.TType;
import com.xiami.service.TTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TTypeServiceImpl implements TTypeService {

    @Resource
    private TTypeMapper tTypeMapper;

    @Override
    public ResponseResult getTypes() {
        List<TType> tTypes = tTypeMapper.selectAll();
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取分类数据成功", tTypes);
    }


    /**
     * 获得分页数据
     *
     * @param typeQueryDto
     * @return
     */
    @Override
    public ResponseResult getTypeList(TypeQueryDto typeQueryDto) {
        PageHelper.startPage(typeQueryDto.getPageNum(), typeQueryDto.getPageSize());
        List<TType> tTypes = tTypeMapper.selectBySearch(typeQueryDto);
        PageInfo pageInfo = new PageInfo(tTypes);
        long total = pageInfo.getTotal();
        PageResult pageResult = new PageResult(total, tTypes);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获得类别分页数据", pageResult);
    }

    @Override
    public ResponseResult addType(TType tType) {
        try {
            int insert = tTypeMapper.insertSelective(tType);
            if (insert > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "新增分类成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "新增分类失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "新增分类失败");
        }
    }

    @Override
    public ResponseResult updateType(TType tType) {
        try {
            tType.setUpdateTime(new Date());
            int insert = tTypeMapper.updateByPrimaryKeySelective(tType);
            if (insert > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "更新分类成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "更新分类失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "更新分类失败");
        }
    }

    @Override
    public ResponseResult deleteType(Integer id) {
        try {
            int i = tTypeMapper.deleteByPrimaryKey(id);
            if (i > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "删除分类成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.OK, "删除分类失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "删除分类失败");
        }
    }

    @Override
    public ResponseResult deleteTypes(Integer[] ids) {
        try {
            int i = tTypeMapper.deleteByIds(ids);
            if (i > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "批量删除分类成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.OK, "批量删除分类失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "批量删除分类失败");
        }
    }
}

