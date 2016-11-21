package com.optimize.code.thread.advance;

import java.util.concurrent.Semaphore;

/**
 * ClassName: SemaphoreTest
 * Desc: 信号量，限定某一个具体资源的最大可访问线程数
 * Date： 2016/11/21
 * Created：shaom
 */
public class SemaphoreTest {

    public static void main(String[] args) throws Exception {
        test1();
        test2();
    }

    /**
     *
     */
    private static void test2() throws Exception {
        Semaphore semaphore = new Semaphore(1);

        semaphore.acquire();//尝试获取一个许可，无法获取，等待或者中断。
        semaphore.acquireUninterruptibly();// 与acquire 类似，但是不响应中断

        semaphore.release();//释放一个许可。
    }

    /**
     * Synchronized\ReentrantLock,一次都只允许一个线程访问一个资源，信号量
     * 却可以指定多个线程同时访问某一个资源。
     */
    private static void test1() {
        // 指定信号量的准入数，即同时能申请多少个许可。
        // 当每个线程每次只申请一个许可时，这就相当于指定同时有多少线程可以访问某一个资源。
        Semaphore semaphore = new Semaphore(1);

        Semaphore semaphore2 = new Semaphore(1, true);//第二个参数可以指定公是否公平

    }
}
