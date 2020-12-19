package com.xiami.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­19 23:12
 */
@RestController
public class TestController {
    @RequestMapping("/say")
    public String say(){
        return "say";
    }
}
