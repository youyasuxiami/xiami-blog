package com.xiami.web;

import com.xiami.base.ResponseResult;
import com.xiami.dto.LoginParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Maps;

import java.util.Map;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­03­28 12:45
 */
@RestController
public class LoginController {
    @GetMapping(value="/login")
    public ResponseResult login(){
        return new ResponseResult(ResponseResult.CodeStatus.OK,"成功",null);
    }

    @PostMapping(value = "/user/login")
    public ResponseResult login(@RequestBody LoginParam loginParam){
        System.out.println("111111111");
        return new ResponseResult(HttpStatus.OK.value(),HttpStatus.OK.toString(),null);
    }
}
