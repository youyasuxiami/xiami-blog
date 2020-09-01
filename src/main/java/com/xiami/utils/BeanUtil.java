package com.xiami.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.DuplicateKeyException;

import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对象操作
 */
public class BeanUtil {

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 非空拷贝
     *
     * @param source
     * @param target
     */
    public static void copyNotNullBean(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }


    public static String[] getCode(DuplicateKeyException e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        //将出错的栈信息输出到printWriter中
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        String pattern = "Duplicate entry '(.+?)' for key '(.+?)'";
        //创建Pattern对象
        Pattern p = Pattern.compile(pattern);
        //创建Matcher对象
        Matcher m = p.matcher(sw.toString());
        String m1 = "";
        if (m.find()) {
            m1 = m.group(0);
        }
        String[] split = m1.split("\'");
        return split;
    }
}
