package com.jdk.base.finaltest;

public class FinalArgsTest {
    public static void main(String[] args)  {
        MyArgClass myClass = new MyArgClass();
        StringBuffer buffer = new StringBuffer("hello");
        myClass.changeValue(buffer);
        System.out.println(buffer.toString());
    }
}

/**
 * 运行这段代码就会发现输出结果为 helloworld。
 * 很显然，用final进行修饰并没有阻止在changeValue中改变buffer指向的对象的内容。
 * 有人说假如把final去掉了，万一在changeValue中让buffer指向了其他对象怎么办。
 * 有这种想法的朋友可以自己动手写代码试一下这样的结果是什么，如果把final去掉了，
 * 然后在changeValue中让buffer指向了其他对象，也不会影响到main方法中的buffer，
 * 原因在于java采用的是值传递，对于引用变量，传递的是引用的值，
 * 也就是说让实参和形参同时指向了同一个对象，因此让形参重新指向另一个对象对实参并没有任何影响。
 */
class MyArgClass {
 
    void changeValue(final StringBuffer buffer) {
        buffer.append("world");
    }
}