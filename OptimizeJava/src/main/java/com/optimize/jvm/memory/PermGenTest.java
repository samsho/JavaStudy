package com.optimize.jvm.memory;

/**
 * ClassName: PermGenTest
 * Desc: 永久区测试
 * Date： 2017/3/15
 * Created：shaom
 */
public class PermGenTest {

    public static void main(String[] args) {
        PermGenTest.test();
    }

    /**
     * 测试永久代的gc
     * -XX:PermSize=1M -XX:MaxPermSize=1M -XX:+PrintGCDetails
     */
    static void test() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String intern = String.valueOf(i).intern(); //加入常量池，常量池放入新生代了
        }
    }
}
