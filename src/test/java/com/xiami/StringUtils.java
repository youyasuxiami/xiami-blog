package com.xiami;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 * 
 * @author linhz
 */
public class StringUtils {
    /**
     * 空字串
     */
    public static final String EMPTY = "";
    private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");
    // private static final String FOLDER_SEPARATOR = "/";
    // private static final char EXTENSION_SEPARATOR = '.';
    
    public static interface GroupHandler {
        
        public String handler(String groupStr);
    }
    
    public static interface GroupsHandler {
        
        public String handler(String[] groupStrs);
    }
    
    public static interface Hander {
        public String[] handler(String[] groupStr);
    }
    
    /**
     * 检测是否为空字符串或为null
     * 
     * @param str
     *            待检测的字符串
     * 
     * @return 若str为null或空字符串则返回true; 否则返加false
     * 
     */
    public static boolean isEmpty(String str) {
        
        return str == null || str.length() <= 0;
        
    }
    
    /**
     * 检测是否不为空字符串且不为null
     * 
     * @param str
     *            待检测的字符串
     * @return 若str不是null且不是空字符串则返回true; 否则返加false
     * 
     */
    public static boolean isNotEmpty(String str) {
        
        return (str != null && str.length() > 0);
        
    }
    
    /**
     * 检测是否为空格串、空字符串或null
     * 
     * @param str
     *            待检测的字符串
     * @return 若str为null或空字符串则或空格串返回true; 否则返加false
     * 
     */
    public static boolean isBlank(String str) {
        // if (isEmpty(str)) {
        // return true;
        // }
        //
        // int strLen = str.length();
        //
        // for (int i = 0; i < strLen; i++) {
        // if (!(Character.isWhitespace(str.charAt(i)))) {
        // return false;
        // }
        // }
        //
        // return true;
        return isNullOrEmpty(str);
    }
    
    public static boolean isNullOrEmpty(final Object str) {
        boolean result = false;
        if (str == null || "null".equals(str)
                || "".equals(str.toString().trim())) {
            result = true;
        }
        return result;
    }
    
    public static String nullObjToStr(final Object str) {
        String result = "";
        if (str == null) {
            result = "";
        } else {
            result = str.toString();
        }
        return result;
    }
    
    /**
     * 检测是否为空格串、空字符串或null
     * 
     * @param str
     *            待检测的字符串
     * @return 若str为null或空字符串则或空格串返回false; 否则返加true
     * 
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    
    /**
     * Check whether the given String has actual text. More specifically,
     * returns <code>true</code> if the string not <code>null<code>,
     * its length is greater than 0, and it contains at least one non-whitespace character.
     * <p><pre>
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     * 
     * @param str
     *            the String to check (may be <code>null</code>)
     * @return <code>true</code> if the String is not <code>null</code>, its
     *         length is greater than 0, and is does not contain whitespace only
     * @see Character#isWhitespace
     */
    public static boolean hasText(String str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check that the given String is neither <code>null</code> nor of length 0.
     * Note: Will return <code>true</code> for a String that purely consists of
     * whitespace.
     * <p>
     *
     * <pre>
     * StringUtils.hasLength(null) = false
     * StringUtils.hasLength(&quot;&quot;) = false
     * StringUtils.hasLength(&quot; &quot;) = true
     * StringUtils.hasLength(&quot;Hello&quot;) = true
     * </pre>
     *
     * @param str
     *            the String to check (may be <code>null</code>)
     * @return <code>true</code> if the String is not null and has length
     * @see #hasText(String)
     */
    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }

    /**
     *
     * 二进制转换为十六进制String
     *
     * @param buff
     *            二进制
     *
     * @return 十六进制String
     *
     */
    public static String byte2hex(byte[] buff) {

        if (buff == null || buff.length <= 0) {
            return EMPTY;
        }

        String tmpStr = null;
        StringBuilder hexStr = new StringBuilder();

        for (int n = 0; n < buff.length; n++) {
            tmpStr = (Integer.toHexString(buff[n] & 0XFF));
            if (tmpStr.length() == 1) {
                hexStr.append("0");
            }
            hexStr.append(tmpStr);
        }
        return hexStr.toString().toUpperCase();
    }

    /**
     * 用StringTokenizer分割字符串到数组
     *
     * @param str
     *            待分割字符串
     * @param sep
     *            分割符
     * @return 分割后的字符串数组
     */
    public static String[] splitTokens(String str, String sep) {
        if (isEmpty(str)) {
            return null;
        }

        StringTokenizer token = new StringTokenizer(str, sep);
        int count = token.countTokens();
        if (count < 1) {
            return null;
        }

        int i = 0;
        String[] output = new String[count];
        while (token.hasMoreTokens()) {
            output[i] = token.nextToken();
            ++i;
        }
        return output;
    }

    /**
     * 用StringTokenizer分割字符串到数组
     *
     * @param str
     *            待分割字符串
     * @param sep
     *            分割符
     * @return 分割后的字符串set容器
     */
    public static Set<String> splitToSet(String str, String sep) {
        Set<String> output = new HashSet<String>();
        if (isEmpty(str)) {
            return output;
        }

        StringTokenizer token = new StringTokenizer(str, sep);
        while (token.hasMoreTokens()) {
            output.add(token.nextToken());
        }

        return output;
    }

    /**
     * 将容器的字符串/数组连接起来
     *
     * @param collection
     *            容器
     * @param sep
     *            分割符
     * @return 字符串
     */
    public static String join(String[] collection, String sep) {
        StringBuilder outStr = new StringBuilder();
        for (Object obj : collection) {
            if (obj == null) {
                continue;
            }
            if (outStr.length() > 0) {
                outStr.append(sep);
            }
            outStr.append(obj);
        }
        return outStr.toString();
    }

    /**
     * 将容器的字符串/数组连接起来
     *
     * @param set
     *            容器
     * @param collection
     *            分割符
     * @param mark
     *            分割字符串的前后增加mark标签符
     * @return 字符串
     */
    public static String join(Collection<?> collection, String sep, char mark) {

        StringBuilder outStr = new StringBuilder();

        for (Object obj : collection) {
            if (obj == null) {
                continue;
            }
            if (outStr.length() > 0) {
                outStr.append(sep);
            }
            outStr.append(mark).append(obj).append(mark);
        }
        return outStr.toString();
    }

    /**
     * 对空串的处理
     *
     * @param input
     *            ：输入的字符串
     * @param def
     *            ：为空返回值
     * @return 如果输入null或空字符串返回def，否则返回trim后字符串
     */
    public static String handleNull(String input, String def) {
        if (isEmpty(input)) {
            return def;
        }

        String trimStr = input.trim();
        if (isEmpty(trimStr)) {
            return def;
        }

        return trimStr;
    }

    /**
     * 判断两个字符串是否相等（大小写敏感）
     *
     * <pre>
     * StringHelper.equals(null, null)   = true
     * StringHelper.equals(null, &quot;abc&quot;)  = false
     * StringHelper.equals(&quot;abc&quot;, null)  = false
     * StringHelper.equals(&quot;abc&quot;, &quot;abc&quot;) = true
     * StringHelper.equals(&quot;abc&quot;, &quot;ABC&quot;) = false
     * </pre>
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * 判断两个字符串是否相等（不区分大小写）
     *
     * <pre>
     * StringHelper.equalsIgnoreCase(null, null)   = true
     * StringHelper.equalsIgnoreCase(null, &quot;abc&quot;)  = false
     * StringHelper.equalsIgnoreCase(&quot;abc&quot;, null)  = false
     * StringHelper.equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
     * StringHelper.equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
     * </pre>
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    /**
     * 去掉字符串中所有的空格/回车/TAB
     *
     * @param str
     *            输入的字符串
     * @return 去掉字符串中所有空格/回车/TAB的字符串
     */
    public static String trimAll(String str) {
        if (isEmpty(str)) {
            return str;
        }

        StringBuffer buf = new StringBuffer(str);
        int index = 0;
        while (buf.length() > index) {
            char c = buf.charAt(index);
            if (Character.isWhitespace(c) || c == '\t' || c == '\r'
                    || c == '\n') {
                buf.deleteCharAt(index);
            } else {
                ++index;
            }
        }

        return buf.toString();
    }

    /**
     * 去掉字符串中前后的空格/回车/TAB
     *
     * @param str
     *            输入的字符串
     * @return 去掉前后空格/回车/TAB的字符串
     */
    public static String trimMore(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);

        // 去掉头部的空格
        int index = 0;
        while (buf.length() > index) {
            char c = buf.charAt(index);
            if (Character.isWhitespace(c) || c == '\t' || c == '\r'
                    || c == '\n') {
                buf.deleteCharAt(index);
            } else {
                break;
            }
        }

        // 去掉尾部的空格
        while (buf.length() > 0) {
            int len = buf.length() - 1;
            char c = buf.charAt(len);
            if (Character.isWhitespace(c) || c == '\t' || c == '\r'
                    || c == '\n') {
                buf.deleteCharAt(len);
            } else {
                break;
            }
        }

        return buf.toString();
    }

    /**
     * 去掉字符串中前后的空格
     *
     * @param str
     *            输入的字符串
     * @return 返回去空格后的字符串
     */
    public static String trim(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.trim();
    }

    /**
     * 第一个字母转大写
     *
     * @param str
     *            输入的字符串
     * @return 首字母为大写的字符串
     */
    public static String first2Upper(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 第一个字母变小写
     *
     * @param str
     *            输入的字符串
     * @return 首字母为小写的字符串
     */
    public static String first2Lower(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 把传进来的字符串的第一个字母转化为小写字母。
     *
     * @param str
     * @return
     */
    public static String firstLetterToUpperCase(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 把传进来的字符串的第一个字母转化为大写字母。
     *
     * @param str
     * @return
     */
    public static String firstLetterToLowerCase(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 把VO的属性名改对应的数据库表的字段名，如helloWorld->hello_world
     *
     * @param columnName
     * @return
     */
    public static String parsePropertyName2ColumnName(String propertyName) {
        StringBuilder result = new StringBuilder();
        if (propertyName != null && propertyName.length() > 0) {
            result.append(propertyName.substring(0, 1).toLowerCase());
            // 循环处理其余字符
            for (int i = 1; i < propertyName.length(); i++) {
                String s = propertyName.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase())
                        && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                result.append(s.toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 把数据库表的字段名改成对应VO的属性名，如ATTR_CODE改为attrCode
     *
     * @param columnName
     * @return
     */
    public static String parseColumnName2PropertyName(String columnName) {
        StringBuffer sb = new StringBuffer();
        boolean flag = false;
        columnName = columnName.toLowerCase();
        for (int i = 0; i < columnName.length(); i++) {
            char ch = columnName.charAt(i);
            if (ch == '_') {
                flag = true;
                continue;
            } else {
                if (flag == true) {
                    sb.append(Character.toUpperCase(ch));
                    flag = false;
                } else {
                    sb.append(ch);
                }
            }
        }

        return sb.toString();
    }

    /**
     * 把点分法的IPV4字符串转化成数字，如输入192.168.2.1，返回192168002001
     *
     * @param ipStr
     *            :类似192.168.2.1
     * @return
     */
    public static Long longFromDotIPStr(String ipStr) {
        if (ipStr == null) {
            throw new IllegalArgumentException();
        }
        StringBuffer sb = new StringBuffer();
        String[] tmp = null;
        Long rst = null;

        tmp = ipStr.split("\\.");
        if (tmp.length != 4) {
            return null;
        }
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i].length() == 1) {
                sb.append("00").append(tmp[i]);
            } else if (tmp[i].length() == 2) {
                sb.append('0').append(tmp[i]);
            } else if (tmp[i].length() == 3) {
                sb.append(tmp[i]);
            } else {
                return null;
            }
        }
        try {
            rst = Long.valueOf(sb.toString());
        } catch (NumberFormatException e) {
            return null;
        }
        return rst;
    }

    /**
     * 把传入的对象转成STRING
     *
     * @param obj
     * @return 若对象不为null则返回其toString()方法返回的值
     */
    public static String ObjectToString(Object obj) {
        if (obj != null)
            return obj.toString();
        return null;
    }

    /**
     * 将Long加到String
     *
     * @param value
     * @param increment
     *            增量
     * @return
     */
    public static Long addLongToString(String value, Long increment) {
        Long v = Long.valueOf(value);
        Long rst = v + increment;
        return rst;
    }

    /**
     * 将String加到Long
     *
     * @param value
     * @param increment
     *            增量
     * @return
     */
    public static Long addStringToLong(Long value, String increment) {
        Long v = Long.valueOf(increment);
        Long rst = v + value;
        return rst;
    }

    /**
     * 对空串的处理
     *
     * @param input
     *            ：输入的字符串
     * @param def
     *            ：为空返回值
     * @return
     */
    public static String handleNULL(String input, String def) {
        if (null == input || input.trim().length() <= 0 || "".equals(input)) {
            return def;
        } else {
            return input.trim();
        }
    }

    /**
     * 判断字符串数组a中是否函数元素s，有则返回true
     *
     * @param a
     *            ：字符串数组
     * @param s
     *            ：字符串
     * @return
     */
    public static boolean containsValue(String[] a, String s) {
        boolean bRetVal = false;
        for (int i = 0; i < a.length; i++) {
            if (a[i].trim().equals(s.trim())) {
                bRetVal = true;
                break;
            }
        }
        return bRetVal;
    }

    /**
     * 将input由A编码格式改成B编码格式，如将"8859_1"格式转为"GB2312"
     *
     * @param input
     *            ：输入语句
     * @param aEncoding
     *            ：A编码格式
     * @param bEncoding
     *            ：B编码格式
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String convertA2BEncoding(String input, String aEncoding,
            String bEncoding) throws UnsupportedEncodingException {
        if (input == null) {
            return "";
        } else {
            input = input.trim();
            return new String(input.getBytes(aEncoding), bEncoding);
        }
    }

    /**
     * 把map转换成url里的参数字符串形式
     * <p>
     * <blockquote>
     *
     * <pre>
     * map.put("name","123"); map.put("code",new String[]{"1234","4567"};
     * map.put("uu",new String("567");
     *
     * String url=urlMapToQueryStr(map); System.out.println(url); ----------
     * name=123&code=1234&code=4567&uu=567
     * </pre>
     *
     * </blockquote>
     * </p>
     *
     * @param map
     * @return
     */
    // public static String urlMapToQueryStr(Map map, String urlEncoding) {
    // return NetUtils.urlMapToQueryStr(map, urlEncoding);
    // }

    /**
     * 判断s是否为空，如果为空，返回""；如果不为空，返回调用s.trim()后的字符串
     *
     * @param s
     * @return
     */
    public static String getNotNullString(String s) {
        if (!StringUtils.hasText(s)) {
            return "";
        } else {
            return s.trim();
        }
    }

    public static String byteToHex(byte b[], boolean upper) {
        String result = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1) {
                result = result + "0" + stmp;
            } else {
                result = result + stmp;
            }
        }
        if (upper) {
            return result.toUpperCase();
        } else {
            return result.toLowerCase();
        }
    }

    public static String firstCharLowCase(String str) {
        return changeFirstCharacterCase(str, false);
    }

    public static String firstCharUpCase(String str) {
        return changeFirstCharacterCase(str, true);
    }

    private static String changeFirstCharacterCase(String str,
            boolean capitalize) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuilder buf = new StringBuilder(str.length());
        if (capitalize) {
            buf.append(Character.toUpperCase(str.charAt(0)));
        } else {
            buf.append(Character.toLowerCase(str.charAt(0)));
        }
        buf.append(str.substring(1));
        return buf.toString();
    }

    /**
     * 如果str为空或者长度为0，返回缺省值
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static String trimWhitespace(String str, String defaultValue) {

        if (!hasLength(str)) {
            return defaultValue;
        }
        StringBuilder buf = new StringBuilder(str);
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
            buf.deleteCharAt(0);
        }
        while (buf.length() > 0
                && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    /**
     * Trim leading and trailing whitespace from the given String.
     *
     * @param str
     *            the String to check
     * @return the trimmed String
     * @see Character#isWhitespace
     */
    public static String trimWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder buf = new StringBuilder(str);
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
            buf.deleteCharAt(0);
        }
        while (buf.length() > 0
                && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    /**
     * Trim leading whitespace from the given String.
     *
     * @param str
     *            the String to check
     * @return the trimmed String
     * @see Character#isWhitespace
     */
    public static String trimLeadingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder buf = new StringBuilder(str);
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
            buf.deleteCharAt(0);
        }
        return buf.toString();
    }

    public static String trimLeadingString(String str, String trimStr) {

        return trimLeadingStrings(str, new String[] { trimStr });

    }

    public static String trimLeadingStrings(String str, String[] trimStrings) {
        if (str == null)
            return null;
        // String tempStr=str;
        StringBuilder sb = new StringBuilder(str);

        while (sb.length() > 0) {

            boolean flag = true;// 可以退出，false表明不能退出
            for (int i = 0; i < trimStrings.length; i++) {

                int index = trimStrings[i].length();
                String ss = "";
                if (index <= sb.length()) {
                    ss = sb.substring(0, index);
                }

                if (ss.equals(trimStrings[i])) {
                    sb.delete(0, index);
                    flag = false;
                }
            }
            // System.out.println(flag);
            if (flag)
                break;

        }
        return sb.toString();
    }

    public static String trimTailStrings(String str, String[] trimStrings) {
        if (str == null)
            return null;
        // String tempStr=str;
        StringBuilder sb = new StringBuilder(str);

        while (sb.length() > 0) {

            boolean flag = true;// 可以退出，false表明不能退出
            for (int i = 0; i < trimStrings.length; i++) {

                int index = sb.length() - trimStrings[i].length();
                String ss = "";
                if (index >= 0 && index < sb.length()) {
                    ss = sb.substring(index, sb.length());
                }
                if (ss.equals(trimStrings[i])) {

                    sb.delete(index, sb.length());
                    flag = false;
                }
            }
            // System.out.println(flag);
            if (flag)
                break;

        }
        return sb.toString();
    }

    public static String trimTailString(String str, String trimStr) {

        return trimTailStrings(str, new String[] { trimStr });

    }

    public static String trimString(String str, String trimStr) {
        if (!hasLength(str)) {
            return str;
        }

        return trimTailString(trimLeadingString(str, trimStr), trimStr);

    }

    public static String[] toStringArray(List<String> list) {

        return list.toArray(new String[0]);

    }

    /**
     * Represents a failed index search.
     *
     * @since 2.1
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * <p>
     * The maximum size to which the padding constant(s) can expand.
     * </p>
     */
    private static final int PAD_LIMIT = 8192;

    /**
     * <p>
     * <code>StringUtils</code> instances should NOT be constructed in standard
     * programming. Instead, the class should be used as
     * <code>StringUtils.trim(" foo ");</code>.
     * </p>
     *
     * <p>
     * This constructor is public to permit tools that require a JavaBean
     * instance to operate.
     * </p>
     */
    public StringUtils() {
        super();
    }

    // Trim
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Removes control characters (char &lt;= 32) from both ends of this String,
     * handling <code>null</code> by returning an empty String ("").
     * </p>
     *
     * <pre>
     * StringUtils.clean(null)          = ""
     * StringUtils.clean("")            = ""
     * StringUtils.clean("abc")         = "abc"
     * StringUtils.clean("    abc    ") = "abc"
     * StringUtils.clean("     ")       = ""
     * </pre>
     *
     * @see String#trim()
     * @param str
     *            the String to clean, may be null
     * @return the trimmed text, never <code>null</code>
     * @deprecated Use the clearer named {@link #trimToEmpty(String)}. Method
     *             will be removed in Commons Lang 3.0.
     */
    @Deprecated
    public static String clean(String str) {
        return str == null ? EMPTY : str.trim();
    }
    
    /**
     * <p>
     * Removes control characters (char &lt;= 32) from both ends of this String
     * returning <code>null</code> if the String is empty ("") after the trim or
     * if it is <code>null</code>.
     * 
     * <p>
     * The String is trimmed using {@link String#trim()}. Trim removes start and
     * end characters &lt;= 32. To strip whitespace use
     * {@link #stripToNull(String)}.
     * </p>
     * 
     * <pre>
     * StringUtils.trimToNull(null)          = null
     * StringUtils.trimToNull("")            = null
     * StringUtils.trimToNull("     ")       = null
     * StringUtils.trimToNull("abc")         = "abc"
     * StringUtils.trimToNull("    abc    ") = "abc"
     * </pre>
     * 
     * @param str
     *            the String to be trimmed, may be null
     * @return the trimmed String, <code>null</code> if only chars &lt;= 32,
     *         empty or null String input
     * @since 2.0
     */
    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }
    
    /**
     * <p>
     * Removes control characters (char &lt;= 32) from both ends of this String
     * returning an empty String ("") if the String is empty ("") after the trim
     * or if it is <code>null</code>.
     * 
     * <p>
     * The String is trimmed using {@link String#trim()}. Trim removes start and
     * end characters &lt;= 32. To strip whitespace use
     * {@link #stripToEmpty(String)}.
     * </p>
     * 
     * <pre>
     * StringUtils.trimToEmpty(null)          = ""
     * StringUtils.trimToEmpty("")            = ""
     * StringUtils.trimToEmpty("     ")       = ""
     * StringUtils.trimToEmpty("abc")         = "abc"
     * StringUtils.trimToEmpty("    abc    ") = "abc"
     * </pre>
     * 
     * @param str
     *            the String to be trimmed, may be null
     * @return the trimmed String, or an empty String if <code>null</code> input
     * @since 2.0
     */
    public static String trimToEmpty(String str) {
        return str == null ? EMPTY : str.trim();
    }
    
    // Stripping
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Strips whitespace from the start and end of a String.
     * </p>
     * 
     * <p>
     * This is similar to {@link #trim(String)} but removes whitespace.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> input String returns <code>null</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.strip(null)     = null
     * StringUtils.strip("")       = ""
     * StringUtils.strip("   ")    = ""
     * StringUtils.strip("abc")    = "abc"
     * StringUtils.strip("  abc")  = "abc"
     * StringUtils.strip("abc  ")  = "abc"
     * StringUtils.strip(" abc ")  = "abc"
     * StringUtils.strip(" ab c ") = "ab c"
     * </pre>
     * 
     * @param str
     *            the String to remove whitespace from, may be null
     * @return the stripped String, <code>null</code> if null String input
     */
    public static String strip(String str) {
        return strip(str, null);
    }
    
    /**
     * <p>
     * Strips whitespace from the start and end of a String returning
     * <code>null</code> if the String is empty ("") after the strip.
     * </p>
     * 
     * <p>
     * This is similar to {@link #trimToNull(String)} but removes whitespace.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     * 
     * <pre>
     * StringUtils.stripToNull(null)     = null
     * StringUtils.stripToNull("")       = null
     * StringUtils.stripToNull("   ")    = null
     * StringUtils.stripToNull("abc")    = "abc"
     * StringUtils.stripToNull("  abc")  = "abc"
     * StringUtils.stripToNull("abc  ")  = "abc"
     * StringUtils.stripToNull(" abc ")  = "abc"
     * StringUtils.stripToNull(" ab c ") = "ab c"
     * </pre>
     * 
     * @param str
     *            the String to be stripped, may be null
     * @return the stripped String, <code>null</code> if whitespace, empty or
     *         null String input
     * @since 2.0
     */
    public static String stripToNull(String str) {
        if (str == null) {
            return null;
        }
        str = strip(str, null);
        return str.length() == 0 ? null : str;
    }
    
    /**
     * <p>
     * Strips whitespace from the start and end of a String returning an empty
     * String if <code>null</code> input.
     * </p>
     * 
     * <p>
     * This is similar to {@link #trimToEmpty(String)} but removes whitespace.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     * 
     * <pre>
     * StringUtils.stripToEmpty(null)     = ""
     * StringUtils.stripToEmpty("")       = ""
     * StringUtils.stripToEmpty("   ")    = ""
     * StringUtils.stripToEmpty("abc")    = "abc"
     * StringUtils.stripToEmpty("  abc")  = "abc"
     * StringUtils.stripToEmpty("abc  ")  = "abc"
     * StringUtils.stripToEmpty(" abc ")  = "abc"
     * StringUtils.stripToEmpty(" ab c ") = "ab c"
     * </pre>
     * 
     * @param str
     *            the String to be stripped, may be null
     * @return the trimmed String, or an empty String if <code>null</code> input
     * @since 2.0
     */
    public static String stripToEmpty(String str) {
        return str == null ? EMPTY : strip(str, null);
    }
    
    /**
     * <p>
     * Strips any of a set of characters from the start and end of a String.
     * This is similar to {@link String#trim()} but allows the characters to be
     * stripped to be controlled.
     * </p>
     * 
     * <p>
     * A <code>null</code> input String returns <code>null</code>. An empty
     * string ("") input returns the empty string.
     * </p>
     * 
     * <p>
     * If the stripChars String is <code>null</code>, whitespace is stripped as
     * defined by {@link Character#isWhitespace(char)}. Alternatively use
     * {@link #strip(String)}.
     * </p>
     * 
     * <pre>
     * StringUtils.strip(null, *)          = null
     * StringUtils.strip("", *)            = ""
     * StringUtils.strip("abc", null)      = "abc"
     * StringUtils.strip("  abc", null)    = "abc"
     * StringUtils.strip("abc  ", null)    = "abc"
     * StringUtils.strip(" abc ", null)    = "abc"
     * StringUtils.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     * 
     * @param str
     *            the String to remove characters from, may be null
     * @param stripChars
     *            the characters to remove, null treated as whitespace
     * @return the stripped String, <code>null</code> if null String input
     */
    public static String strip(String str, String stripChars) {
        if (isEmpty(str)) {
            return str;
        }
        str = stripStart(str, stripChars);
        return stripEnd(str, stripChars);
    }
    
    /**
     * <p>
     * Strips any of a set of characters from the start of a String.
     * </p>
     * 
     * <p>
     * A <code>null</code> input String returns <code>null</code>. An empty
     * string ("") input returns the empty string.
     * </p>
     * 
     * <p>
     * If the stripChars String is <code>null</code>, whitespace is stripped as
     * defined by {@link Character#isWhitespace(char)}.
     * </p>
     * 
     * <pre>
     * StringUtils.stripStart(null, *)          = null
     * StringUtils.stripStart("", *)            = ""
     * StringUtils.stripStart("abc", "")        = "abc"
     * StringUtils.stripStart("abc", null)      = "abc"
     * StringUtils.stripStart("  abc", null)    = "abc"
     * StringUtils.stripStart("abc  ", null)    = "abc  "
     * StringUtils.stripStart(" abc ", null)    = "abc "
     * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     * 
     * @param str
     *            the String to remove characters from, may be null
     * @param stripChars
     *            the characters to remove, null treated as whitespace
     * @return the stripped String, <code>null</code> if null String input
     */
    public static String stripStart(String str, String stripChars) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while ((start != strLen)
                    && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((start != strLen)
                    && (stripChars.indexOf(str.charAt(start)) != -1)) {
                start++;
            }
        }
        return str.substring(start);
    }
    
    /**
     * <p>
     * Strips any of a set of characters from the end of a String.
     * </p>
     * 
     * <p>
     * A <code>null</code> input String returns <code>null</code>. An empty
     * string ("") input returns the empty string.
     * </p>
     * 
     * <p>
     * If the stripChars String is <code>null</code>, whitespace is stripped as
     * defined by {@link Character#isWhitespace(char)}.
     * </p>
     * 
     * <pre>
     * StringUtils.stripEnd(null, *)          = null
     * StringUtils.stripEnd("", *)            = ""
     * StringUtils.stripEnd("abc", "")        = "abc"
     * StringUtils.stripEnd("abc", null)      = "abc"
     * StringUtils.stripEnd("  abc", null)    = "  abc"
     * StringUtils.stripEnd("abc  ", null)    = "abc"
     * StringUtils.stripEnd(" abc ", null)    = " abc"
     * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
     * </pre>
     * 
     * @param str
     *            the String to remove characters from, may be null
     * @param stripChars
     *            the characters to remove, null treated as whitespace
     * @return the stripped String, <code>null</code> if null String input
     */
    public static String stripEnd(String str, String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }
        
        if (stripChars == null) {
            while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((end != 0)
                    && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
                end--;
            }
        }
        return str.substring(0, end);
    }
    
    // StripAll
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Strips whitespace from the start and end of every String in an array.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     * 
     * <p>
     * A new array is returned each time, except for length zero. A
     * <code>null</code> array will return <code>null</code>. An empty array
     * will return itself. A <code>null</code> array entry will be ignored.
     * </p>
     * 
     * <pre>
     * StringUtils.stripAll(null)             = null
     * StringUtils.stripAll([])               = []
     * StringUtils.stripAll(["abc", "  abc"]) = ["abc", "abc"]
     * StringUtils.stripAll(["abc  ", null])  = ["abc", null]
     * </pre>
     * 
     * @param strs
     *            the array to remove whitespace from, may be null
     * @return the stripped Strings, <code>null</code> if null array input
     */
    public static String[] stripAll(String[] strs) {
        return stripAll(strs, null);
    }
    
    /**
     * <p>
     * Strips any of a set of characters from the start and end of every String
     * in an array.
     * </p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     * 
     * <p>
     * A new array is returned each time, except for length zero. A
     * <code>null</code> array will return <code>null</code>. An empty array
     * will return itself. A <code>null</code> array entry will be ignored. A
     * <code>null</code> stripChars will strip whitespace as defined by
     * {@link Character#isWhitespace(char)}.
     * </p>
     * 
     * <pre>
     * StringUtils.stripAll(null, *)                = null
     * StringUtils.stripAll([], *)                  = []
     * StringUtils.stripAll(["abc", "  abc"], null) = ["abc", "abc"]
     * StringUtils.stripAll(["abc  ", null], null)  = ["abc", null]
     * StringUtils.stripAll(["abc  ", null], "yz")  = ["abc  ", null]
     * StringUtils.stripAll(["yabcz", null], "yz")  = ["abc", null]
     * </pre>
     * 
     * @param strs
     *            the array to remove characters from, may be null
     * @param stripChars
     *            the characters to remove, null treated as whitespace
     * @return the stripped Strings, <code>null</code> if null array input
     */
    public static String[] stripAll(String[] strs, String stripChars) {
        int strsLen;
        if (strs == null || (strsLen = strs.length) == 0) {
            return strs;
        }
        String[] newArr = new String[strsLen];
        for (int i = 0; i < strsLen; i++) {
            newArr[i] = strip(strs[i], stripChars);
        }
        return newArr;
    }
    
    // IndexOf
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Finds the first index within a String, handling <code>null</code>. This
     * method uses {@link String#indexOf(int)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> or empty ("") String will return <code>-1</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.indexOf(null, *)         = -1
     * StringUtils.indexOf("", *)           = -1
     * StringUtils.indexOf("aabaabaa", 'a') = 0
     * StringUtils.indexOf("aabaabaa", 'b') = 2
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchChar
     *            the character to find
     * @return the first index of the search character, -1 if no match or
     *         <code>null</code> string input
     * @since 2.0
     */
    public static int indexOf(String str, char searchChar) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.indexOf(searchChar);
    }
    
    /**
     * <p>
     * Finds the first index within a String from a start position, handling
     * <code>null</code>. This method uses {@link String#indexOf(int, int)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> or empty ("") String will return <code>-1</code>. A
     * negative start position is treated as zero. A start position greater than
     * the string length returns <code>-1</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.indexOf(null, *, *)          = -1
     * StringUtils.indexOf("", *, *)            = -1
     * StringUtils.indexOf("aabaabaa", 'b', 0)  = 2
     * StringUtils.indexOf("aabaabaa", 'b', 3)  = 5
     * StringUtils.indexOf("aabaabaa", 'b', 9)  = -1
     * StringUtils.indexOf("aabaabaa", 'b', -1) = 2
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchChar
     *            the character to find
     * @param startPos
     *            the start position, negative treated as zero
     * @return the first index of the search character, -1 if no match or
     *         <code>null</code> string input
     * @since 2.0
     */
    public static int indexOf(String str, char searchChar, int startPos) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.indexOf(searchChar, startPos);
    }
    
    /**
     * <p>
     * Finds the first index within a String, handling <code>null</code>. This
     * method uses {@link String#indexOf(String)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> String will return <code>-1</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.indexOf(null, *)          = -1
     * StringUtils.indexOf(*, null)          = -1
     * StringUtils.indexOf("", "")           = 0
     * StringUtils.indexOf("aabaabaa", "a")  = 0
     * StringUtils.indexOf("aabaabaa", "b")  = 2
     * StringUtils.indexOf("aabaabaa", "ab") = 1
     * StringUtils.indexOf("aabaabaa", "")   = 0
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @return the first index of the search String, -1 if no match or
     *         <code>null</code> string input
     * @since 2.0
     */
    public static int indexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.indexOf(searchStr);
    }
    
    /**
     * <p>
     * Finds the n-th index within a String, handling <code>null</code>. This
     * method uses {@link String#indexOf(String)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> String will return <code>-1</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.ordinalIndexOf(null, *, *)          = -1
     * StringUtils.ordinalIndexOf(*, null, *)          = -1
     * StringUtils.ordinalIndexOf("", "", *)           = 0
     * StringUtils.ordinalIndexOf("aabaabaa", "a", 1)  = 0
     * StringUtils.ordinalIndexOf("aabaabaa", "a", 2)  = 1
     * StringUtils.ordinalIndexOf("aabaabaa", "b", 1)  = 2
     * StringUtils.ordinalIndexOf("aabaabaa", "b", 2)  = 5
     * StringUtils.ordinalIndexOf("aabaabaa", "ab", 1) = 1
     * StringUtils.ordinalIndexOf("aabaabaa", "ab", 2) = 4
     * StringUtils.ordinalIndexOf("aabaabaa", "", 1)   = 0
     * StringUtils.ordinalIndexOf("aabaabaa", "", 2)   = 0
     * </pre>
     * 
     * <p>
     * Note that 'head(String str, int n)' may be implemented as:
     * </p>
     * 
     * <pre>
     * str.substring(0, lastOrdinalIndexOf(str, &quot;\n&quot;, n))
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @param ordinal
     *            the n-th <code>searchStr</code> to find
     * @return the n-th index of the search String, <code>-1</code> (
     *         <code>INDEX_NOT_FOUND</code>) if no match or <code>null</code>
     *         string input
     * @since 2.1
     */
    public static int ordinalIndexOf(String str, String searchStr, int ordinal) {
        return ordinalIndexOf(str, searchStr, ordinal, false);
    }
    
    /**
     * <p>
     * Finds the n-th index within a String, handling <code>null</code>. This
     * method uses {@link String#indexOf(String)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> String will return <code>-1</code>.
     * </p>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @param ordinal
     *            the n-th <code>searchStr</code> to find
     * @param lastIndex
     *            true if lastOrdinalIndexOf() otherwise false if
     *            ordinalIndexOf()
     * @return the n-th index of the search String, <code>-1</code> (
     *         <code>INDEX_NOT_FOUND</code>) if no match or <code>null</code>
     *         string input
     */
    // Shared code between ordinalIndexOf(String,String,int) and
    // lastOrdinalIndexOf(String,String,int)
    private static int ordinalIndexOf(String str, String searchStr,
            int ordinal, boolean lastIndex) {
        if (str == null || searchStr == null || ordinal <= 0) {
            return INDEX_NOT_FOUND;
        }
        if (searchStr.length() == 0) {
            return lastIndex ? str.length() : 0;
        }
        int found = 0;
        int index = lastIndex ? str.length() : INDEX_NOT_FOUND;
        do {
            if (lastIndex) {
                index = str.lastIndexOf(searchStr, index - 1);
            } else {
                index = str.indexOf(searchStr, index + 1);
            }
            if (index < 0) {
                return index;
            }
            found++;
        } while (found < ordinal);
        return index;
    }
    
    /**
     * <p>
     * Finds the first index within a String, handling <code>null</code>. This
     * method uses {@link String#indexOf(String, int)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> String will return <code>-1</code>. A negative start
     * position is treated as zero. An empty ("") search String always matches.
     * A start position greater than the string length only matches an empty
     * search String.
     * </p>
     * 
     * <pre>
     * StringUtils.indexOf(null, *, *)          = -1
     * StringUtils.indexOf(*, null, *)          = -1
     * StringUtils.indexOf("", "", 0)           = 0
     * StringUtils.indexOf("aabaabaa", "a", 0)  = 0
     * StringUtils.indexOf("aabaabaa", "b", 0)  = 2
     * StringUtils.indexOf("aabaabaa", "ab", 0) = 1
     * StringUtils.indexOf("aabaabaa", "b", 3)  = 5
     * StringUtils.indexOf("aabaabaa", "b", 9)  = -1
     * StringUtils.indexOf("aabaabaa", "b", -1) = 2
     * StringUtils.indexOf("aabaabaa", "", 2)   = 2
     * StringUtils.indexOf("abc", "", 9)        = 3
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @param startPos
     *            the start position, negative treated as zero
     * @return the first index of the search String, -1 if no match or
     *         <code>null</code> string input
     * @since 2.0
     */
    public static int indexOf(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }
        // JDK1.2/JDK1.3 have a bug, when startPos > str.length for "", hence
        if (searchStr.length() == 0 && startPos >= str.length()) {
            return str.length();
        }
        return str.indexOf(searchStr, startPos);
    }
    
    /**
     * <p>
     * Case in-sensitive find of the first index within a String.
     * </p>
     * 
     * <p>
     * A <code>null</code> String will return <code>-1</code>. A negative start
     * position is treated as zero. An empty ("") search String always matches.
     * A start position greater than the string length only matches an empty
     * search String.
     * </p>
     * 
     * <pre>
     * StringUtils.indexOfIgnoreCase(null, *)          = -1
     * StringUtils.indexOfIgnoreCase(*, null)          = -1
     * StringUtils.indexOfIgnoreCase("", "")           = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "a")  = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "b")  = 2
     * StringUtils.indexOfIgnoreCase("aabaabaa", "ab") = 1
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @return the first index of the search String, -1 if no match or
     *         <code>null</code> string input
     * @since 2.5
     */
    public static int indexOfIgnoreCase(String str, String searchStr) {
        return indexOfIgnoreCase(str, searchStr, 0);
    }
    
    /**
     * <p>
     * Case in-sensitive find of the first index within a String from the
     * specified position.
     * </p>
     * 
     * <p>
     * A <code>null</code> String will return <code>-1</code>. A negative start
     * position is treated as zero. An empty ("") search String always matches.
     * A start position greater than the string length only matches an empty
     * search String.
     * </p>
     * 
     * <pre>
     * StringUtils.indexOfIgnoreCase(null, *, *)          = -1
     * StringUtils.indexOfIgnoreCase(*, null, *)          = -1
     * StringUtils.indexOfIgnoreCase("", "", 0)           = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
     * StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", -1) = 2
     * StringUtils.indexOfIgnoreCase("aabaabaa", "", 2)   = 2
     * StringUtils.indexOfIgnoreCase("abc", "", 9)        = 3
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @param startPos
     *            the start position, negative treated as zero
     * @return the first index of the search String, -1 if no match or
     *         <code>null</code> string input
     * @since 2.5
     */
    public static int indexOfIgnoreCase(String str, String searchStr,
            int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }
        if (startPos < 0) {
            startPos = 0;
        }
        int endLimit = (str.length() - searchStr.length()) + 1;
        if (startPos > endLimit) {
            return -1;
        }
        if (searchStr.length() == 0) {
            return startPos;
        }
        for (int i = startPos; i < endLimit; i++) {
            if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
                return i;
            }
        }
        return -1;
    }
    
    // LastIndexOf
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Finds the last index within a String, handling <code>null</code>. This
     * method uses {@link String#lastIndexOf(int)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> or empty ("") String will return <code>-1</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.lastIndexOf(null, *)         = -1
     * StringUtils.lastIndexOf("", *)           = -1
     * StringUtils.lastIndexOf("aabaabaa", 'a') = 7
     * StringUtils.lastIndexOf("aabaabaa", 'b') = 5
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchChar
     *            the character to find
     * @return the last index of the search character, -1 if no match or
     *         <code>null</code> string input
     * @since 2.0
     */
    public static int lastIndexOf(String str, char searchChar) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.lastIndexOf(searchChar);
    }
    
    /**
     * <p>
     * Finds the last index within a String from a start position, handling
     * <code>null</code>. This method uses {@link String#lastIndexOf(int, int)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> or empty ("") String will return <code>-1</code>. A
     * negative start position returns <code>-1</code>. A start position greater
     * than the string length searches the whole string.
     * </p>
     * 
     * <pre>
     * StringUtils.lastIndexOf(null, *, *)          = -1
     * StringUtils.lastIndexOf("", *,  *)           = -1
     * StringUtils.lastIndexOf("aabaabaa", 'b', 8)  = 5
     * StringUtils.lastIndexOf("aabaabaa", 'b', 4)  = 2
     * StringUtils.lastIndexOf("aabaabaa", 'b', 0)  = -1
     * StringUtils.lastIndexOf("aabaabaa", 'b', 9)  = 5
     * StringUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
     * StringUtils.lastIndexOf("aabaabaa", 'a', 0)  = 0
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchChar
     *            the character to find
     * @param startPos
     *            the start position
     * @return the last index of the search character, -1 if no match or
     *         <code>null</code> string input
     * @since 2.0
     */
    public static int lastIndexOf(String str, char searchChar, int startPos) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.lastIndexOf(searchChar, startPos);
    }
    
    /**
     * <p>
     * Finds the last index within a String, handling <code>null</code>. This
     * method uses {@link String#lastIndexOf(String)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> String will return <code>-1</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.lastIndexOf(null, *)          = -1
     * StringUtils.lastIndexOf(*, null)          = -1
     * StringUtils.lastIndexOf("", "")           = 0
     * StringUtils.lastIndexOf("aabaabaa", "a")  = 0
     * StringUtils.lastIndexOf("aabaabaa", "b")  = 2
     * StringUtils.lastIndexOf("aabaabaa", "ab") = 1
     * StringUtils.lastIndexOf("aabaabaa", "")   = 8
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @return the last index of the search String, -1 if no match or
     *         <code>null</code> string input
     * @since 2.0
     */
    public static int lastIndexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.lastIndexOf(searchStr);
    }
    
    /**
     * <p>
     * Finds the n-th last index within a String, handling <code>null</code>.
     * This method uses {@link String#lastIndexOf(String)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> String will return <code>-1</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.lastOrdinalIndexOf(null, *, *)          = -1
     * StringUtils.lastOrdinalIndexOf(*, null, *)          = -1
     * StringUtils.lastOrdinalIndexOf("", "", *)           = 0
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 1)  = 7
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 2)  = 6
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 1)  = 5
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 2)  = 2
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 1) = 4
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2) = 1
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 1)   = 8
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 2)   = 8
     * </pre>
     * 
     * <p>
     * Note that 'tail(String str, int n)' may be implemented as:
     * </p>
     * 
     * <pre>
     * str.substring(lastOrdinalIndexOf(str, &quot;\n&quot;, n) + 1)
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @param ordinal
     *            the n-th last <code>searchStr</code> to find
     * @return the n-th last index of the search String, <code>-1</code> (
     *         <code>INDEX_NOT_FOUND</code>) if no match or <code>null</code>
     *         string input
     * @since 2.5
     */
    public static int lastOrdinalIndexOf(String str, String searchStr,
            int ordinal) {
        return ordinalIndexOf(str, searchStr, ordinal, true);
    }
    
    /**
     * <p>
     * Finds the first index within a String, handling <code>null</code>. This
     * method uses {@link String#lastIndexOf(String, int)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> String will return <code>-1</code>. A negative start
     * position returns <code>-1</code>. An empty ("") search String always
     * matches unless the start position is negative. A start position greater
     * than the string length searches the whole string.
     * </p>
     * 
     * <pre>
     * StringUtils.lastIndexOf(null, *, *)          = -1
     * StringUtils.lastIndexOf(*, null, *)          = -1
     * StringUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
     * StringUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
     * StringUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
     * StringUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
     * StringUtils.lastIndexOf("aabaabaa", "b", -1) = -1
     * StringUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
     * StringUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @param startPos
     *            the start position, negative treated as zero
     * @return the first index of the search String, -1 if no match or
     *         <code>null</code> string input
     * @since 2.0
     */
    public static int lastIndexOf(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.lastIndexOf(searchStr, startPos);
    }
    
    /**
     * <p>
     * Case in-sensitive find of the last index within a String.
     * </p>
     * 
     * <p>
     * A <code>null</code> String will return <code>-1</code>. A negative start
     * position returns <code>-1</code>. An empty ("") search String always
     * matches unless the start position is negative. A start position greater
     * than the string length searches the whole string.
     * </p>
     * 
     * <pre>
     * StringUtils.lastIndexOfIgnoreCase(null, *)          = -1
     * StringUtils.lastIndexOfIgnoreCase(*, null)          = -1
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A")  = 7
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B")  = 5
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB") = 4
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @return the first index of the search String, -1 if no match or
     *         <code>null</code> string input
     * @since 2.5
     */
    public static int lastIndexOfIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return lastIndexOfIgnoreCase(str, searchStr, str.length());
    }
    
    /**
     * <p>
     * Case in-sensitive find of the last index within a String from the
     * specified position.
     * </p>
     * 
     * <p>
     * A <code>null</code> String will return <code>-1</code>. A negative start
     * position returns <code>-1</code>. An empty ("") search String always
     * matches unless the start position is negative. A start position greater
     * than the string length searches the whole string.
     * </p>
     * 
     * <pre>
     * StringUtils.lastIndexOfIgnoreCase(null, *, *)          = -1
     * StringUtils.lastIndexOfIgnoreCase(*, null, *)          = -1
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 8)  = 7
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 8)  = 5
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB", 8) = 4
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 9)  = 5
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", -1) = -1
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 0)  = -1
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @param startPos
     *            the start position
     * @return the first index of the search String, -1 if no match or
     *         <code>null</code> string input
     * @since 2.5
     */
    public static int lastIndexOfIgnoreCase(String str, String searchStr,
            int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }
        if (startPos > (str.length() - searchStr.length())) {
            startPos = str.length() - searchStr.length();
        }
        if (startPos < 0) {
            return -1;
        }
        if (searchStr.length() == 0) {
            return startPos;
        }
        
        for (int i = startPos; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
                return i;
            }
        }
        return -1;
    }
    
    // Contains
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Checks if String contains a search character, handling <code>null</code>.
     * This method uses {@link String#indexOf(int)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> or empty ("") String will return <code>false</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.contains(null, *)    = false
     * StringUtils.contains("", *)      = false
     * StringUtils.contains("abc", 'a') = true
     * StringUtils.contains("abc", 'z') = false
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchChar
     *            the character to find
     * @return true if the String contains the search character, false if not or
     *         <code>null</code> string input
     * @since 2.0
     */
    public static boolean contains(String str, char searchChar) {
        if (isEmpty(str)) {
            return false;
        }
        return str.indexOf(searchChar) >= 0;
    }
    
    /**
     * <p>
     * Checks if String contains a search String, handling <code>null</code>.
     * This method uses {@link String#indexOf(String)}.
     * </p>
     * 
     * <p>
     * A <code>null</code> String will return <code>false</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.contains(null, *)     = false
     * StringUtils.contains(*, null)     = false
     * StringUtils.contains("", "")      = true
     * StringUtils.contains("abc", "")   = true
     * StringUtils.contains("abc", "a")  = true
     * StringUtils.contains("abc", "z")  = false
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @return true if the String contains the search String, false if not or
     *         <code>null</code> string input
     * @since 2.0
     */
    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return str.indexOf(searchStr) >= 0;
    }
    
    /**
     * <p>
     * Checks if String contains a search String irrespective of case, handling
     * <code>null</code>. Case-insensitivity is defined as by
     * {@link String#equalsIgnoreCase(String)}.
     * 
     * <p>
     * A <code>null</code> String will return <code>false</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.contains(null, *) = false
     * StringUtils.contains(*, null) = false
     * StringUtils.contains("", "") = true
     * StringUtils.contains("abc", "") = true
     * StringUtils.contains("abc", "a") = true
     * StringUtils.contains("abc", "z") = false
     * StringUtils.contains("abc", "A") = true
     * StringUtils.contains("abc", "Z") = false
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param searchStr
     *            the String to find, may be null
     * @return true if the String contains the search String irrespective of
     *         case or false if not or <code>null</code> string input
     */
    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        int len = searchStr.length();
        int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }
    
    public static String removeHtmlTags(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        Matcher m = REMOVE_TAGS.matcher(string);
        return m.replaceAll("");
    }
    private static final Pattern pattern = Pattern.compile("\\{(.*?)\\}");
    private static Matcher matcher;
    public static String stringFormat(String sourStr, Map<String, Object> param) {
        String tagerStr = sourStr;
        if (param == null)
            return tagerStr;
        matcher = pattern.matcher(tagerStr);
        while (matcher.find()) {
            String key = matcher.group();
            String keyclone = key.substring(1, key.length() - 1).trim();
            Object value = param.get(keyclone);
            if (value != null)
                tagerStr = tagerStr.replace(key, value.toString());
        }
        return tagerStr;
    }
}
