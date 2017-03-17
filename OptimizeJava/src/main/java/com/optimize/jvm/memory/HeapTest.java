package com.optimize.jvm.memory;

import java.util.ArrayList;

/**
 * ClassName: HeapTest
 * Desc:  堆
 * Date： 2017/3/15
 * Created：shaom
 */
public class HeapTest {

    public static void main(String[] args) {
//        test();
        test1();

    }

    /**
     * 堆溢出
     * -Xmx5M：java.lang.OutOfMemoryError: Java heap space
     * -Xmx11M:顺利跑完
     */
    static void test() {
        ArrayList<byte[]> list = new ArrayList<byte[]>();

        for (int i = 1; i < 10; i++) {
            byte[] bytes = new byte[1024 * 1024];
            list.add(bytes);
            System.out.println(i + "M 被分配");
        }
        System.out.println("Max Memory:" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M");
    }

    /**
     * 每次分配1M内存，累计3M，清空内存
     * 更小的 -Xms ，更多的GC
     * -Xms4M -Xmx10M -verbose:gc  3次minor gc ,1次full gc
     * -Xms10M -Xmx10M -verbose:gc  2次minor gc ,1次full gc
     *
     * -Xms10M -Xmx10M -Xmn2M -verbose:gc -XX:+PrintGCDetails 3次minor gc ,1次full gc
     * -Xms10M -Xmx10M -Xmn3M -verbose:gc -XX:+PrintGCDetails 2次minor gc ,1次full gc
     */
    static void test1() {
        ArrayList<byte[]> list = new ArrayList<byte[]>();

        for (int i = 1; i < 10; i++) {
            byte[] bytes = new byte[1024 * 1024];//1M
            list.add(bytes);
//            System.out.println(i + "M 被分配");
            if (list.size() == 3) {// 3M清空
                list.clear();
            }
        }
    }
}
