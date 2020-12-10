package com.xiami.dao;

import com.xiami.dto.TypeQueryDto;
import com.xiami.entity.TType;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TTypeMapper extends MyMapper<TType> {
    List<TType> selectBySearch(TypeQueryDto typeQueryDto);

    int deleteByIds(Integer[] ids);

    List<String> getAllTypeNames();

}