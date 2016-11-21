package com.optimize.code.thread.advance;

/**
 * ClassName: VolatileTest
 * Desc: 内存模型（Volatile主内存的一块区域）
 * Date： 2016/11/21
 * Created：shaom
 */
public class VolatileTest {

    public static void main(String[] args) {
        test1();
    }

    /**
     * 每个线程都有自己的工作内存，共享的变量存放在主内存，每个线程中有这个变量的拷贝。
     * 因此当一个线程改变自己的工作内存中的数据时，对其他线程拉私活，肯呢过是不可见的。
     * Volatile即是主内存的一块区域，使得该变量在多线程间可见。
     */
    private static void test1() {

    }
}
