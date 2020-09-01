package com.xiami.utils;

import com.xiami.dao.SysDictionaryMapper;
import com.xiami.entity.SysDictionary;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Description：获取字典表的中文
 * 执行顺序：Constructor >> @Autowired >> @PostConstruct
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­30 18:32
 */
@Component
public class DictionaryUtils {

    private static DictionaryUtils dictionaryUtils;

    @Resource
    private SysDictionaryMapper sysDictionaryMapper;

    /**
     * @PostConstruct注解的方法将会在依赖注入完成后被自动调用。
     */
    @PostConstruct
    public void init() {
        dictionaryUtils = this;
        dictionaryUtils.sysDictionaryMapper = this.sysDictionaryMapper;
    }

    /**
     * 根据字典表的group的值：获取所有下拉框的值
     *
     * @param group
     * @return
     */
    public static List<SysDictionary> getDictionaryList(String group) {
        Example example = new Example(SysDictionary.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("group", group);
        List<SysDictionary> sysDictionaries = dictionaryUtils.sysDictionaryMapper.selectByExample(example);
        return sysDictionaries;
    }

    public static String toChinese(String group, String code) {
        Example example = new Example(SysDictionary.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("group", group);
        criteria.andEqualTo("code", code);
        List<SysDictionary> sysDictionaries = dictionaryUtils.sysDictionaryMapper.selectByExample(example);
        String s = sysDictionaries.get(0).getValue();
        //List<String> lists = sysDictionaries.stream().map(SysDictionary::getValue).collect(Collectors.toList());
        //String s = lists.get(0);
        return s;
    }


}
