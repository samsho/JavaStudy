package com.jdk.eight.lambda;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * ClassName: Lambda
 * Description:
 * Date: 2016/7/7
 * Time: 15:01
 *
 * @author sm12652
 * @version V1.0.6
 */
public class Lambda {

    /**
     * 匿名类解决的问题
     * 将指定目录下所有的文件过滤出来，并且打印出这些文件的完整路径
     */
    public void innerClass() {
        File dir = new File("D:/");
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });

        // lambda () -> {}
        File[] files2 = dir.listFiles(
                (File pathname) -> {
                    return pathname.isFile();
                }
        );

        // 如果一个Lambda表达式中的参数类型都可以被推断出来，那么我们甚至可以忽略参数的类型声明
        File[] files3 = dir.listFiles(pathname -> {
            return pathname.isFile();
        });

        // 方法引用
        File[] files4 = dir.listFiles(File::isFile);

        for (File f : files) {
            System.out.println(f.getAbsolutePath());
        }
    }


    /**
     * 函数接口
     * Runnable、FileFilter，还有Closable、Readable、Callable、Iterable、Comparable
     */
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(i);
                }
            }
        }).start();

        //  当参数列表为空时，我们依然需要提供一对圆括号
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(i);
            }
        }).start();


        //public static <T> void sort(T[] a, Comparator<? super T> c)
        String[] words = new String[]{"AAA", "BBBB", "CCCCC", "DDDDDD"};
        Arrays.sort(words, (one, theOther) -> Integer.compare(one.length(), theOther.length()));

    }


    /**
     * 在Java 8中引入了一个专门的java.util.function包。这个包里面定义了很多函数接口
     * Predicate接口:
     * 这个接口代表的是一类接受一个参数并且返回一个boolean值的功能抽象。
     */
    public void predicate() {
/*        Predicate<T> p = (File pathname) -> {
            pathname.isFile();
        };*/
    }

    /**
     * Consumer接口
     * 这个接口接收一个参数，然后不返回任何值
     */
    public void consumer() {
        String[] strs = new String[]{"AAA", "BBBB", "CCCCC", "DDDDDD"};
        List<String> list = Arrays.asList(strs);
        list.stream().forEach((str) -> {
            System.out.println(str);
        });
    }

    /**
     * 方法引用
     * 很多时候，当我们要将一个功能传递给其他代码，但是刚好有一些现有的方法能够实现这样的功能。
     * 我们完全没有必要写一个Lambda表达式，把这个已经的方法的实现逻辑再写一遍，
     * 我们直接把这个已有的方法传递过去不就好了吗？
     * 那么方法引用就提供了这样的便利性。
     */
    public void method() {
        String[] strs = new String[]{"AAA", "BBBB", "CCCCC", "DDDDDD"};
        List<String> list = Arrays.asList(strs);
        list.stream().forEach(System.out::println);


        // 方法引用主要有三种形式：
        // 方法引用都等价于给方法提供参数的Lambda表达式 x -> System.out.println(x)
//        object::instanceMethod
//        Class::staticMethod  x.method


//        Class::instanceMethod // 第三种情况下，方法引用适用的第一个参数就变成方法的目标对象
//        (x, y) -> {x.compareToIgnoreCase(y)};
    }

    /**
     * 方法引用可以使用this和super关键词。
     * 当使用this::instanceMethod实际上this指代的对象就成为了方法的目标对象(也就是在this上调用方法)。
     * 而super::instanceMethod则表示调用this对象父类所实现的方法版本。
     */
    static class Person {
        public void sayHello() {
            System.out.println("Hello!");
        }
    }

    static  class Student extends Person{
        @Override
        public void sayHello() {
            Thread thread = new Thread(super::sayHello);
            thread.start();
        }
    }

    public static class MethodReference {
        public static void main(String[] args) {
            Student student = new Student();
            student.sayHello();//Hello!
        }
    }

    /**
     * .构造器引用
     *
     */
    public void constructor () {

        //一个字符串形式表示的数字的列表转换成整型数组
        // <R> Stream<R> map(Function<? super T, ? extends R> mapper);
        // Integer int = new Integer(String str);
        String[] arr = new String[]{"1", "2", "3"};
        List<String> list = Arrays.asList(arr);
        Stream<Integer> stream = list.stream().map(Integer::new);
        Integer[] integers = stream.toArray(Integer[]::new);
    }

}
