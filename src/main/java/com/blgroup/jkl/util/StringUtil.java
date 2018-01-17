package com.blgroup.jkl.util;

import java.security.MessageDigest;
import java.util.List;

import com.blgroup.jkl.enums.Symbol;


/**
 * 字符串工具类
 * 
 * @version 1.0
 * 
 * @createDate 2012-7-31
 * 
 * @modifyDate 2012-8-14
 */
public class StringUtil {

    /**
     * 默认的空值
     */
    public static final String EMPTY = "";

    /**
     * 转换成字符串,如果对象为Null,则返回空字符串
     * 
     * @param obj
     *            需要判断的对象数组
     * @return boolean
     */
    public static String valueOf(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return "";
    }

    /**
     * 检查字符串是否为空
     * 
     * @param str
     *            字符串
     * @return 是否为空
     */
    public static boolean isEmpty(Object str) {
        if (str == null || valueOf(str).length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查字符串是否不为空
     * 
     * @param str
     *            字符串
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    /**
     * 截取并保留标志位之前的字符串
     * 
     * @param str
     *            字符串
     * @param expr
     *            分隔符
     * @return 字符串
     */
    public static String substringBefore(String str, String expr) {
        if (isEmpty(str) || expr == null) {
            return str;
        }
        if (expr.length() == 0) {
            return EMPTY;
        }
        int pos = str.indexOf(expr);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 截取并保留标志位之后的字符串
     * 
     * @param str
     *            字符串
     * @param expr
     *            分隔符
     * @return 字符串
     */
    public static String substringAfter(String str, String expr) {
        if (isEmpty(str)) {
            return str;
        }
        if (expr == null) {
            return EMPTY;
        }
        int pos = str.indexOf(expr);
        if (pos == -1) {
            return EMPTY;
        }
        return str.substring(pos + expr.length());
    }

    /**
     * 截取并保留最后一个标志位之前的字符串
     * 
     * @param str
     *            字符串
     * @param expr
     *            分隔符
     * @return 字符串
     */
    public static String substringBeforeLast(String str, String expr) {
        if (isEmpty(str) || isEmpty(expr)) {
            return str;
        }
        int pos = str.lastIndexOf(expr);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 截取并保留最后一个标志位之后的字符串
     * 
     * @param str
     *            字符串
     * @param expr
     *            分隔符
     * @return 字符串
     */
    public static String substringAfterLast(String str, String expr) {
        if (isEmpty(str)) {
            return str;
        }
        if (isEmpty(expr)) {
            return EMPTY;
        }
        int pos = str.lastIndexOf(expr);
        if (pos == -1 || pos == (str.length() - expr.length())) {
            return EMPTY;
        }
        return str.substring(pos + expr.length());
    }

    /**
     * 把字符串按分隔符转换为数组
     * 
     * @param string
     *            字符串
     * @param expr
     *            分隔符
     * @return 字符串数组
     */
    public static String[] split(String string, String expr) {
        return string.split(expr);
    }

    /**
     * 去除字符串中的空格
     * 
     * @param str
     *            字符串
     * @return 去除空格后的结果
     */
    public static String noSpace(String str) {
        str = str.trim();
        str = str.replace(" ", "_");
        return str;
    }

    /**
     * 截取指定长度的字符串，并将被截掉的字符转化为...
     * 
     * @param str
     *            要截取的字符串
     * @param length
     *            要截取的长度
     * @return 字符串
     */
    public static String toSubSuspension(String str, int length) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        str = str.substring(0, length) + "...";
        return str;
    }

    /**
     * 比较字符串是否相等
     * 
     * @param str1
     *            字符串
     * @param str2
     *            字符串
     * @return boolean
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if ("".equals(str1) && "".equals(str2)) {
            return true;
        }
        return str1.equals(str2);
    }

    /**
     * 将list中的字符串以指定的分隔符进行拼接
     * 如果分隔符为null,则以默认分隔符进行拼接
     * 
     * @param list
     *            数据源
     * @param separator
     *            分隔符
     * @return 以指定分隔符拼接的字符串
     */
    public static String listToString(List<String> list, String separator) {
        if (CollectionUtil.isEmpty(list)) {
            return "";
        }

        separator = separator == null ? Symbol.Comma.toString() : separator;
        StringBuffer sb = new StringBuffer("");
        for (String item : list) {
            sb.append(item == null ? "" : item);
            sb.append(separator);
        }
        sb = sb.length() > 0 ? sb.deleteCharAt(sb.length() - 1) : sb;

        return sb.toString();
    }

    /**
     * 将list中的字符串以系统默认的分隔符进行拼接
     * 
     * @param list
     *            数据源
     * @return 以系统默认分隔符拼接的字符串
     */
    public static String listToString(List<String> list) {
        return listToString(list, Symbol.Comma.toString());
    }

    /**
     * 获取字符串的长度
     * 
     * @param value
     * @return
     */
    public static int length(String value) {
        if (StringUtil.isEmpty(value)) {
            return 0;
        }
        return value.length();
    }

    /**
     * 获取字符串的长度，中英混合字符串
     * 
     * @param value
     * @param chineseLength
     *            一个中文代表的长度
     * @return
     */
    public static int length(String value, int chineseLength) {
        if (StringUtil.isEmpty(value)) {
            return 0;
        }
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为chineseLength，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度 */
                valueLength += chineseLength;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 去空处理
     * @author yyc
     * @param value
     * @return
     */
    public static String trim(String value) {
        return null == value ? "" : value.trim();
    }

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
