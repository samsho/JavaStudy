package com.jdk.base.finaltest;

public class StaticAndFinalTest {
    public static void main(String[] args)  {
        MyClass myClass1 = new MyClass();
        MyClass myClass2 = new MyClass();
        System.out.println(myClass1.i);
        System.out.println(myClass1.j);
        System.out.println(myClass2.i);
        System.out.println(myClass2.j);

        // 0.15415554449879154
        // 0.9587856732258498
        // 0.5316859516738236
        // 0.9587856732258498

        /**
         * 运行这段代码就会发现，每次打印的两个j值都是一样的，而i的值却是不同的。
         * 从这里就可以知道final和static变量的区别了。
         *
         * static作用于成员变量用来表示只保存一份副本，而final的作用是用来保证变量不可变。
         * **/
 
    }
}

/**
 * final 与 static 的区别
 */
class MyClass {
    public final double i = Math.random();// 保证变量不可变。每个对象都有自己的
    public static double j = Math.random();//只保存一份副本,不管多少个对象，都是同一份
}