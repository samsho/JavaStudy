package com.jdk.eight.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ClassName: Stream
 * Description:
 * Date: 2016/7/7
 * Time: 16:04
 *
 * @author sm12652
 * @version V1.0.6
 */
public class StreamTest {

    public void wordCount() {
        List<String> wordList = new ArrayList<>();
        int count = 0;
        for (String word : wordList) {
            if (word.length() > 5) {
                System.out.println(word);
                count++;
            }
        }
    }

    /**
     * Stream的版本
     */
    public void wordCountStream() {
        List<String> wordList = new ArrayList<>();
        long count = wordList.stream().filter(word -> word.length() > 5).count();

        //并行
        long count2 = wordList.parallelStream().filter(word -> word.length() > 5).count();
    }

    /**
     * 创建Stream
     */
    public void create() {
        Stream<String> stream = Stream.of("I", "am", "wawlian");

        // Arrays类提供了一个stream(int[] array, int startInclusive, int endExclusive)和一系列重载方法来指定数组中哪些元素来创建Stream。
        Arrays.stream(new String[2]);

        // 空
        Stream<Object> empty = Stream.empty();

        // 可以使用Stream的generate()方法来创建无限的Stream。
        Stream.generate(() -> {
            return "Hello";
        });
        Stream.generate(() -> "Hello");
        Stream.generate(() -> Math.random());
        Stream.generate(Math::random);// Class::staticMethod

        // Stream中还有一个iterate方法，Function
        Stream<Integer> iterate = Stream.iterate(1, x -> x + 1);
    }

    /**
     * 一些操作
     */
    public void operate() {

        // filter方法:这个接口实际上表现的是一种判定的规则
        Stream.of("1").filter((t) -> {
            return t.equals("1");
        });
        Stream.of("1").filter(t -> t.equals("1"));

        new ArrayList<>().stream().filter(t -> t.equals("1"));

        // map方法: map方法实际上是对集合中的所有元素执行某些变换操作，并生成新的Stream方法
        new ArrayList<String>().stream().map(String::toUpperCase);
        new ArrayList<String>().stream().map(String::toUpperCase);

//        取出每个字符串第一个字符的例子
        new ArrayList<String>().stream().map(s -> s.charAt(0));

        // 截取子Stream
        Stream.generate(Math::random).limit(5);
        Stream.iterate(1, x -> x + 1).limit(2);

        // 拼接Stream操作:concat

        // 有状态的中间操作。
        new ArrayList<String>().stream().sorted((t, t2) -> t.length() - t2.length());
    }

    public void reduce() {

    }

    public void collection() {

        Set<String> collect = new ArrayList<String>().stream().collect(Collectors.toSet());

        Map<String, String> collect1 = new ArrayList<String>().stream().collect(Collectors.toMap(String::toUpperCase, String::toLowerCase));
    }
}
