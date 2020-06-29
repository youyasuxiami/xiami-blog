package com.xiami.web;

import com.alibaba.fastjson.JSONArray;
import com.xiami.base.ResponseResult;
import com.xiami.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­30 17:19
 */
@RequestMapping("/menu")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping(value = "/menuList")
    public ResponseResult menuList(String pageNum,String pageSize){
        //try {
            return menuService.getMenuJsonList();
            //writeJson(response, ja,ResultCodes.successCode, "查询成功！");
        //} catch (Exception e) {
        //     TODO: handle exception
            //writeJson(response, null,ResultCodes.exceptionResult, "查询！");
        //}

    }
}
