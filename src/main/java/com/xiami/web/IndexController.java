package com.xiami.web;

import com.xiami.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­04 11:04
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        //int i=9/0;
        //String blog=null;
        //if(blog==null){
        //    throw new NotFoundException("博客不存在");
        //}
        System.out.println("----------index-----------");
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {
        System.out.println("--------------blog");
        return "blog";
    }
}
