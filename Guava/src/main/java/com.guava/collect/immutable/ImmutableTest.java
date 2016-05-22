package com.guava.collect.immutable;

import com.entity.Presider;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.ImmutableTable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by home
 * date 2016/3/8.
 */
public class ImmutableTest {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
//        test4();
    }

    /**
     * 　　Immutable集合使用方法：
     * 　　一个immutable集合可以有以下几种方式来创建：
     * 　　1.用copyOf方法, 譬如, ImmutableSet.copyOf(set)
     * 　　2.使用of方法，譬如，ImmutableSet.of("a", "b", "c")或者ImmutableMap.of("a", 1, "b", 2)
     * 　　3.使用Builder类
     */
    public static void test1() {

        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println("list：" + list);//list：[a, b, c]

        // 用copyOf方法
        ImmutableList<String> imlist = ImmutableList.copyOf(list);//defensive copy
        System.out.println("imlist：" + imlist);//imlist：[a, b, c]

        // 使用of方法
        ImmutableList<String> imOflist = ImmutableList.of("peida", "jerry", "harry");
        System.out.println("imOflist：" + imOflist);//imOflist：[peida, jerry, harry]
        // 对有序不可变集合来说
        ImmutableSortedSet<String> imSortList = ImmutableSortedSet.of("a", "b", "c", "a", "d", "b");
        System.out.println("imSortList：" + imSortList);//imSortList：[a, b, c, d]

        list.add("baby");
        System.out.println("list add a item after list:" + list);//[a, b, c, baby]
        System.out.println("list add a item after imlist:" + imlist);//[a, b, c],不可变

        list.clear();
        System.out.println("list add a item after list:" + list);//[]
        System.out.println("list add a item after imlist:" + imlist);//[a, b, c]

        // 使用Builder类
        ImmutableSet<Color> imColorSet = ImmutableSet.<Color>builder()
                .add(new Color(0, 255, 255))
                .add(new Color(0, 191, 255))
                .build();

        System.out.println("imColorSet:" + imColorSet);//[java.awt.Color[r=0,g=255,b=255], java.awt.Color[r=0,g=191,b=255]]
    }

    public static void test2() {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");

        ImmutableList<String> imlist = ImmutableList.copyOf(list);//defensive copy
        System.out.println(imlist.get(1));//b
        System.out.println(imlist.asList());//[a, b, c]
        System.out.println(imlist.asList().get(2));//c
        System.out.println(imlist.reverse().asList());//[c, b, a]
    }

    public static void test3() {
        ImmutableSet<String> imColorSet = ImmutableSet.<String>builder()
                .add("a")
                .add("f")
                .add("d")
                .add("c")
                .add("b")
                .build();
        System.out.println("imColorSet:" + imColorSet);//imColorSet:[a, f, d, c, b]
        System.out.println(imColorSet.asList().get(2));// set转成list,d
    }

    /**
     * 对有序不可变集合来说
     */
    public static void test4() {
        ImmutableSortedSet<String> sortedSet = ImmutableSortedSet.of("a", "b", "c", "a", "d", "b");
        System.out.println(sortedSet);//[a, b, c, d]
    }

}
