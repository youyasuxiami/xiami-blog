package com.xiami.controller;

import com.xiami.annotation.OperatorLog;
import com.xiami.base.ResponseResult;
import com.xiami.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­30 20:38
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/about")
public class AboutController {

    @Autowired
    private AboutService aboutService;


    @RequestMapping("/getMe")
    public ResponseResult getMe(Integer userId) {
        ResponseResult blogs = aboutService.getMe(1);
        return blogs;
    }
}
