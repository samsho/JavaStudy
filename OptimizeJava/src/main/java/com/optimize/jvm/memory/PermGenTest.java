package com.optimize.jvm.memory;

/**
 * ClassName: PermGenTest
 * Desc: ����������
 * Date�� 2017/3/15
 * Created��shaom
 */
public class PermGenTest {

    public static void main(String[] args) {
        PermGenTest.test();
    }

    /**
     * �������ô���gc
     * -XX:PermSize=1M -XX:MaxPermSize=1M -XX:+PrintGCDetails
     */
    static void test() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String intern = String.valueOf(i).intern(); //���볣���أ������ط�����������
        }
    }
}
