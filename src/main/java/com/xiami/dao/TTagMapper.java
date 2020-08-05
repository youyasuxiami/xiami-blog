package com.xiami.dao;

import com.xiami.dto.TagQueryDto;
import com.xiami.entity.TTag;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TTagMapper extends MyMapper<TTag> {
    List<TTag> selectBySearch(TagQueryDto tagQueryDto);

    int deleteByIds(Integer[] ids);
}