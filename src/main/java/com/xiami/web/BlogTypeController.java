package com.xiami.web;

import com.xiami.base.ResponseResult;
import com.xiami.dto.TypeQueryDto;
import com.xiami.entity.TType;
import com.xiami.service.TTypeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/type")
public class BlogTypeController {

    @Resource
    private TTypeService tTypeService;

    @GetMapping("/getTypes")
    public ResponseResult getTypes() {
        return tTypeService.getTypes();
    }

    /**
     * 分页显示分类数据
     *
     * @param typeQueryDto
     * @return
     */
    @GetMapping("/getTypeList")
    public ResponseResult getTypeList(TypeQueryDto typeQueryDto) {
        return tTypeService.getTypeList(typeQueryDto);
    }

    /**
     * 新增/编辑
     *
     * @param tType
     * @return
     */
    @PostMapping("/addType")
    public ResponseResult addType(@RequestBody TType tType) {
        if (null == tType.getId()) {
            //新增
            return tTypeService.addType(tType);
        }
        //编辑
        return tTypeService.updateType(tType);
    }

    @DeleteMapping("/deleteType")
    public ResponseResult deleteType(Integer id) {
        return tTypeService.deleteType(id);
    }

    @DeleteMapping("/deleteTypes")
    public ResponseResult deleteTypes(Integer[] ids) {
        return tTypeService.deleteTypes(ids);
    }
}
