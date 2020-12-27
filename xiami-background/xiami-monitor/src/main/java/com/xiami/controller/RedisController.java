package com.xiami.controller;

import com.xiami.base.ResponseResult;
import com.xiami.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/monitor/redis")
public class RedisController {

    private final RedisService redisService;

    @GetMapping()
    public ResponseResult getInfo() {
        return ResponseResult.ok(redisService.getInfo(),"获取redis信息成功");
    }
}