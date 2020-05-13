package com.xiami.blog;

import com.xiami.blog.dao.TBlogMapper;
import com.xiami.blog.entity.TBlog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private TBlogMapper tBlogMapper;
    @Test
    void contextLoads() {
        List<TBlog> tBlogs = tBlogMapper.selectAll();
        System.out.println(tBlogs);
    }

}
