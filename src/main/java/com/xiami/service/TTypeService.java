package com.xiami.service;

import com.xiami.base.ResponseResult;
import com.xiami.dto.TypeQueryDto;
import com.xiami.entity.TType;

public interface TTypeService {

    ResponseResult getTypes();

    ResponseResult getTypeList(TypeQueryDto typeQueryDto);

    ResponseResult addType(TType tType);

    ResponseResult updateType(TType tType);

    ResponseResult deleteType(Integer id);

    ResponseResult deleteTypes(Integer[] ids);
}

