package com.xiami.web;

import com.xiami.base.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­03­28 12:45
 */
@RestController
public class LoginController {
    @RequestMapping(value="/login",method = RequestMethod.GET)
    public ResponseResult login(){
        return new ResponseResult(ResponseResult.CodeStatus.OK,"成功","成功了");
    }
}
