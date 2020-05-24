package com.xiami;

import com.xiami.dao.TBlogMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.UserParam;
import com.xiami.entity.TBlog;
import com.xiami.entity.User;
import com.xiami.utils.MapperUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private TBlogMapper tBlogMapper;

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        List<TBlog> tBlogs = tBlogMapper.selectAll();
        System.out.println(tBlogs);
    }

    @Test
    void getUserInfo() {
        //List<User> users = userMapper.selectAll();
        //users.stream().forEach(user -> {
        //    System.out.println(user);
        //});

        //User user=new User();
        //user.setId(2);
        //User user1 = userMapper.selectOne(user);
        //System.out.println(user1);

        Example example=new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("nickName","xiami");
        User user = userMapper.selectOneByExample(example);
        System.out.println(user.getNickName());
    }

    /**
     * 传输层dto转换为json格式
     * @throws Exception
     */
    @Test
    void testPrintJson() throws Exception {
        System.out.println(MapperUtils.obj2json(new UserParam()));
        //{"id":null,"nickName":null,"password":null,"name":null,"sex":null,"age":null,"phone":null,"email":null,"avatar":null,"createTime":null,"updateTime":null,"loginTime":null,"ps":null,"status":null}


    }

}
