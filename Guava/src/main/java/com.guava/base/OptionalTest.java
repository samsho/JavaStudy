package com.guava.base;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Strings;

import java.util.Set;

/**
 * Created by home
 * date 2016/3/9.
 * http://ifeve.com/google-guava-using-and-avoiding-null/
 *
 */
public class OptionalTest {

    public static void main(String[] args) {
//        test1();
        test2();
//        test3();
    }

    static void test1(){
        Optional<Integer> possible = Optional.of(5);
        System.out.println(possible.isPresent());//true
        System.out.println(possible.get());//5

    }

    static void test2(){
        /*创建Optional实例（以下都是静态方法）*/

        //创建指定引用的Optional实例，若引用为null则快速失败
        Optional<Integer> optional1 = Optional.of(5);

        //创建引用缺失的Optional实例
        Optional<Integer> optional2 = Optional.absent();

        //创建指定引用的Optional实例，若引用为null则表示缺失
        Optional<Integer> optional3 = Optional.fromNullable(5);

        /*用Optional实例查询引用（以下都是非静态方法）：*/
        //如果Optional包含非null的引用（引用存在），返回true
        System.out.println("optional1 isPresent " + optional1.isPresent());//true
        System.out.println("optional2 isPresent " + optional2.isPresent());//false
        System.out.println("optional3 isPresent " + optional3.isPresent());//true

        //返回Optional所包含的引用，若引用缺失，则抛出java.lang.IllegalStateException
        System.out.println("optional1 get " + optional1.get());//5
//        System.out.println("optional2 get " + optional2.get());
        System.out.println("optional3 get " + optional3.get());//5


        //返回Optional所包含的引用，若引用缺失，返回指定的值
        System.out.println("optional1 or " + optional1.or(99));//5
        System.out.println("optional2 or " + optional2.or(99));//99
        System.out.println("optional3 or " + optional3.or(99));//5

        //返回Optional所包含的引用，若引用缺失，返回null
        System.out.println("optional1 orNull " + optional1.orNull());//5
        System.out.println("optional2 orNull " + optional2.orNull());//null
        System.out.println("optional3 orNull " + optional3.orNull());//5

        //返回Optional所包含引用的单例不可变集，如果引用存在，返回一个只有单一元素的集合，如果引用缺失，返回一个空集合。
        System.out.println(optional1.asSet().isEmpty());//false
        System.out.println(optional2.asSet().isEmpty());//true
        System.out.println(optional3.asSet().isEmpty());//false

    }

    static void test3(){

        //当你需要用一个默认值来替换可能的null
        System.out.println(Optional.of(4).or(6));//4不存在，就用6
        System.out.println(Objects.firstNonNull(null, 6));
        System.out.println(MoreObjects.firstNonNull(null, 6));// MoreObjects 代替 Objects


        //其他方法
        System.out.println(Strings.emptyToNull(""));
        System.out.println(Strings.nullToEmpty(null));
        System.out.println(Strings.isNullOrEmpty(""));
    }

}
