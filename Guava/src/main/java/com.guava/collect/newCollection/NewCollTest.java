package com.guava.collect.newCollection;

import com.google.common.collect.*;

import java.util.*;

/**
 * ClassName: NewCollTest
 * Description:
 * Date: 2016/3/10 16:15
 *
 * @author sm12652
 * @version V1.0
 */
public class NewCollTest {

    public static void main(String[] args) {
//        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
    }

    /**
     * Multiset
     */
    static void test1(){

        List<String> words = Lists.newArrayList("a","b","d","g","b","d","d","g","b");

        //统计一个词在文档中出现了多少次，传统的做法是这样的：
        Map<String, Integer> counts = new HashMap<String, Integer>();
        for (String word : words) {
            Integer count = counts.get(word);
            if (count == null) {
                counts.put(word, 1);
            } else {
                counts.put(word, count + 1);
            }
        }

        System.out.println(counts);

        /*------------------------------------*/
        Multiset<String> multiset = HashMultiset.create();
        multiset.addAll(words);
        System.out.println(multiset);
        System.out.println(multiset.elementSet());
        System.out.println(multiset.count("a"));
        System.out.println(multiset.size());
        System.out.println(multiset.entrySet());

    }

    /**
     * Multimap
     * Map<K, List<V>>或Map<K, Set<V>>
     * ListMultimap或SetMultimap
     */
    static void test2(){

        ListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("a","1");
        multimap.put("a","2");
        multimap.put("1", "3");
        multimap.put("A", "4");
        multimap.asMap();
        System.out.println(multimap.get("a"));

//        SetMultimap<> setMultimap = HashMultimap.create();

    }

    /**
     * BiMap
     */
    static void test3(){


    }
    static void test4(){}
    static void test5(){}
    static void test6(){}
    static void test7(){}
}
