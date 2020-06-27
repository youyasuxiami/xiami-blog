package com.xiami;

import com.xiami.dao.SysDictionaryMapper;
import com.xiami.dao.TBlogMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.UserParam;
import com.xiami.dto.UserQueryDto;
import com.xiami.entity.SysDictionary;
import com.xiami.entity.TBlog;
import com.xiami.entity.User;
import com.xiami.utils.DictionaryUtils;
import com.xiami.utils.MapperUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private TBlogMapper tBlogMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    @Autowired
    DictionaryUtils dictionaryUtils;

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

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", "zhengjin");
        User user = userMapper.selectOneByExample(example);
        System.out.println(user.getNickName());
    }

    /**
     * 传输层dto转换为json格式
     *
     * @throws Exception
     */
    @Test
    void testPrintJson() throws Exception {
        System.out.println(MapperUtils.obj2json(new UserQueryDto()));
        //{"id":null,"nickName":null,"password":null,"name":null,"sex":null,"age":null,"phone":null,"email":null,"avatar":null,"createTime":null,"updateTime":null,"loginTime":null,"ps":null,"status":null}


    }

    //根据参数获取字典表中文
    @Test
    public void tranSysDictionaries() {
        Example example = new Example(SysDictionary.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("group", "sys_role");
        List<SysDictionary> sysDictionaries = sysDictionaryMapper.selectByExample(example);
        List<String> collect =
                sysDictionaries.stream()
                        //.filter( sysDictionary -> "管理员".equals(sysDictionary.getValue()))
                        //.limit(2)
                        //.skip(0)
                        //.distinct()
                        //.forEach(System.out::println);
                        .map(SysDictionary::getValue)
                        .collect(Collectors.toList());
        collect.stream().forEach(System.out::println);
        //sysDictionaries.stream().forEach(sysDictionary -> {
        //    System.out.println("----------");
        //    System.out.println(sysDictionary);
        //});
    }

    @Test
    public void test1() {

        //List<SysDictionary> sys_role = DictionaryUtils.getDictionaryList("sys_role");

        dictionaryUtils.getDictionaryList("sys_role").stream()
                .map(SysDictionary::getValue)
                .forEach(System.out::println);
    }

    @Test
    public void test2() {
        BigDecimal bigDecimal = new BigDecimal(22.4);
        System.out.println(17 & 13);
    }

    @Test
    public void TestDecimalFormat() {
        double pi = 314.1415927;
        System.out.println(new DecimalFormat("0").format(pi));
        System.out.println(new DecimalFormat("#").format(pi));
    }
}
