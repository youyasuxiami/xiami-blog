package com.xiami.web;

import com.xiami.base.ResponseResult;
import com.xiami.entity.SysDictionary;
import com.xiami.utils.DictionaryUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­30 23:41
 */
@RequestMapping("/utils")
@RestController
public class UtilsController {

    @Resource
    private DictionaryUtils dictionaryUtils;


    @GetMapping("/dictionaries")
    public ResponseResult<List<SysDictionary>> getDictoryListByGroup(String group) {
        List<SysDictionary> dictionaryList = dictionaryUtils.getDictionaryList(group);
        if (dictionaryList != null && dictionaryList.isEmpty()) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "获取数据失败");
        } else {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取数据成功", dictionaryList);
        }
    }
}
