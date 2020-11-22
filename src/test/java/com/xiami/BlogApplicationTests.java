package com.xiami;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiami.base.ResponseResult;
import com.xiami.dao.RolePermissionMapper;
import com.xiami.dao.SysDictionaryMapper;
import com.xiami.dao.TBlogMapper;
import com.xiami.dao.TBlogTagsMapper;
import com.xiami.dao.TCommentReportMapper;
import com.xiami.dao.TTagMapper;
import com.xiami.dao.TTypeMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.UserQueryDto;
import com.xiami.entity.RolePermission;
import com.xiami.entity.SysDictionary;
import com.xiami.entity.TBlog;
import com.xiami.entity.TBlogTags;
import com.xiami.entity.TTag;
import com.xiami.entity.TType;
import com.xiami.entity.User;
import com.xiami.entity.UserInfo;
import com.xiami.service.ProfileService;
import com.xiami.utils.DictionaryUtils;
import com.xiami.utils.MapperUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private ProfileService profileService;

    @Resource
    private TTypeMapper tTypeMapper;

    @Resource
    private TTagMapper tTagMapper;

    @Resource
    private TCommentReportMapper tCommentReportMapper;


    @Resource
    private TBlogTagsMapper tBlogTagsMapper;

    @Test
    void test1() throws JsonProcessingException {
        UserInfo userInfo = new UserInfo();
        ObjectMapper objectMapper = new ObjectMapper();

        //将对象序列化为json字符串
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); //忽略为null的字段
        String userJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userInfo);
        System.out.println(userJsonString);


        //将json反序列化为java对象
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        UserInfo userInfo2 = objectMapper.readValue(userJsonString, UserInfo.class);
        System.out.println(userInfo2);
    }







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
        criteria.andEqualTo("name", "鸭鸭小仙女");
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
    public void test100() {

        //List<SysDictionary> sys_role = DictionaryUtils.getDictionaryList("sys_role");

        dictionaryUtils.getDictionaryList("sys_role").stream()
                .map(SysDictionary::getValue)
                .forEach(System.out::println);
    }

    @Test
    public void test200() {
        BigDecimal bigDecimal = new BigDecimal(22.4);
        System.out.println(17 & 13);
    }

    @Test
    public void TestDecimalFormat() {
        double pi = 314.1415927;
        System.out.println(new DecimalFormat("0").format(pi));
        System.out.println(new DecimalFormat("#").format(pi));
    }

    @Test
    public void TestDelete() {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(56);
        List<RolePermission> select = rolePermissionMapper.select(rolePermission);
        System.out.println(1111);
    }

    @Test
    public void say() {
        String a = "111";
        String b = "111";
        String c = new String("111");
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(c.hashCode());
    }

    @Test
    public void testFirstMenus() {
        String name="zhengjin";
        ResponseResult firstMenus = profileService.getFirstMenus();
        System.out.println(firstMenus);

    }

    @Test
    public void getTypes() {
        List<TType> tTypes = tTypeMapper.selectAll();
        System.out.println(tTypes);
    }

    @Test
    public void getTags() {
        List<TTag> tTags = tTagMapper.selectAll();
        System.out.println(tTags);
    }

    @Test
    public void getBlogs() {
        List<TBlog> tBlogs = tBlogMapper.selectAll();
        System.out.println(tBlogs);
    }


    @Test
    public void insertType() {
        List<TType> tTypes = tTypeMapper.selectAll();
        TType tType = new TType();
        tType.setName("1");

        //int insert = tTypeMapper.insert(tType);
        //System.out.println(tType);
        //System.out.println(insert);
        //TType(id=11, name=111, createTime=null, updateTime=null)
        //1


        int insert = tTypeMapper.insertSelective(tType);
        System.out.println(tType);
        System.out.println(insert);
        //TType(id=12, name=222, createTime=null, updateTime=null)
        //1

        //int insert = tTypeMapper.insertUseGeneratedKeys(tType);
        //System.out.println(tType);
        //System.out.println(insert);
        //TType(id=13, name=333, createTime=null, updateTime=null)
        //1
    }

    @Test
    void test120(){
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int year = aCalendar.get(Calendar.YEAR);
        int month = aCalendar.get(Calendar.MONTH) + 1;
        int tian = aCalendar.getActualMaximum(Calendar.DATE);
        int dayOfMonth = aCalendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(year);
        System.out.println(month);
        System.out.println(tian);
        System.out.println(dayOfMonth);


    }


    @Test
    void test130() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = sdf.parse("2020-9");//搜索的字符串时间-->date
        int monthSpace = DateUtils.getMonthSpace(date);
        System.out.println(monthSpace);
        //Date date1=new Date();
    }

    @Test
    void test(){
        //获取该博客的所有标签
        TBlogTags tBlogTags=new TBlogTags();
        tBlogTags.setBlogsId(11);
        List<Integer> collect = tBlogTagsMapper.select(tBlogTags).stream().map(TBlogTags::getTagsId).collect(Collectors.toList());

    }

}
