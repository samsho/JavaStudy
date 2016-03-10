package com.guava.base;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

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
        test2();
        test3();
        test4();
        test5();
    }

    static void test1(){
        Strings.emptyToNull("");
        Strings.nullToEmpty("");
        Strings.isNullOrEmpty("");
    }

    static void test2(){
        System.out.println(Splitter.on(',').split("foo,bar,qux"));
        System.out.println(Splitter.on(',').splitToList("foo,bar,qux"));
    }


    static void test3(){
        // padStart("7", 3, '0') "007"
        // padStart("2010", 3, '0') "2010"
        Strings.padStart("7", 3, '0');
        Strings.padStart("2010", 3, '0');


        // padEnd("4.", 5, '0') "4.000"
        // padEnd("2010", 3, '!') "2010"
        Strings.padEnd("4.", 5, '0');
        Strings.padEnd("2010.", 5, '0');
    }
    static void test4(){}
    static void test5(){}
}
