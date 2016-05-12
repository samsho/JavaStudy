package com.guava.base;

import com.google.common.base.*;
import com.google.common.collect.Lists;

import java.io.UnsupportedEncodingException;

/**
 * ClassName: StringTest
 * Description:
 * Date: 2016/3/10 15:17
 *
 * @author sm12652
 * @version V1.0
 */
public class StringTest {
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
//        test5();
//        test6();
    }

    /**
     * Strings
     */
    static void test1(){
        System.out.println(Strings.emptyToNull(""));//null
        System.out.println(Strings.nullToEmpty(null));
        System.out.println(Strings.isNullOrEmpty(""));//true


        // padStart("7", 3, '0') "007"
        // padStart("2010", 3, '0') "2010"
        System.out.println(Strings.padStart("7", 3, '0'));//007
        System.out.println(Strings.padStart("2010", 3, '0'));//2010

        // padEnd("4.", 5, '0') "4.000"
        // padEnd("2010", 3, '!') "2010."
        System.out.println(Strings.padEnd("4.", 5, '0'));//4.000
        System.out.println(Strings.padEnd("2010.", 6, '0'));//2010.0
    }

    /**
     * Splitter
     *
     */
    static void test2(){
        System.out.println(Splitter.on(',').split("foo,bar,qux"));//[foo, bar, qux]
        System.out.println(Splitter.on(',').splitToList("foo,bar,qux"));//[foo, bar, qux]

        System.out.println(Splitter.on(',')
                .trimResults()//移除结果字符串的前导空白和尾部空白
                .omitEmptyStrings()//从结果中自动忽略空字符串
                .limit(2)//限制拆分出的字符串数量
                .split("foo,bar,,   qux"));//[foo, bar,,   qux]

        Lists.newArrayList(Splitter.on(',').split("foo,bar,qux"));
    }

    /**
     * 连接器[Joiner]
     * joiner实例总是不可变的。用来定义joiner目标语义的配置方法总会返回一个新的joiner实例。
     * 这使得joiner实例都是线程安全的，你可以将其定义为static final常量。
     */
    static void test3(){
        //skipNulls()方法是直接忽略null
        Joiner joiner = Joiner.on("; ").skipNulls();
        System.out.println(joiner.join("Harry", null, "Ron", "Hermione"));//Harry; Ron; Hermione

        // useForNull(String)方法可以给定某个字符串来替换null
        System.out.println(Joiner.on("; ")
                .useForNull("null")
                .join("Harry", null, "Ron", "Hermione"));//Harry; null; Ron; Hermione
    }


    /**
     * 字符匹配器[CharMatcher]
     * CharMatcher实例代表着某一类字符;CharMatcher实例就是对字符的布尔判断
     * 让你对字符作特定类型的操作：修剪[trim]、折叠[collapse]、移除[remove]、保留[retain]等等
     */
    static void test4(){

        String noControl = CharMatcher.JAVA_ISO_CONTROL.removeFrom(""); //移除control字符
        String theDigits = CharMatcher.DIGIT.retainFrom("a10,er,kk;45"); //只保留数字字符
        System.out.println(theDigits);//1045

        //去除两端的空格，并把中间的连续空格替换成单个空格
        String spaced = CharMatcher.WHITESPACE.trimAndCollapseFrom(" hello trim nijij ", ' ');
        System.out.println(spaced);//hello trim nijij

        String noDigits = CharMatcher.JAVA_DIGIT.replaceFrom("df1f23gh45ghj6fgh", "*"); //用*号替换所有数字
        System.out.println(noDigits);//df*f**gh**ghj*fgh

        // 只保留数字和小写字母
        String lowerAndDigit = CharMatcher.JAVA_DIGIT.or(CharMatcher.JAVA_LOWER_CASE).retainFrom("AAAabfh17665jhhsAAAGHJ");
        System.out.println(lowerAndDigit);//abfh17665jhhs
    }

    /**
     * 字符集[Charsets]
     */
    static void test5(){
       byte[] bytes = null;
        try {
            bytes = "String".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // how can this possibly happen?
            throw new AssertionError(e);
        }

        bytes = "String".getBytes(Charsets.UTF_8);
    }

    /**
     * 大小写格式[CaseFormat]
     */
    static void test6() {
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME"));//constantName
    }
}
