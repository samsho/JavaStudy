package com.optimize.code.thread.excutor.defined;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: ThreadPoolExecutorTest
 * Desc: 自定义线程池 ThreadPoolExecutor
 * Date： 2016/11/21
 * Created：shaom
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {


//        test1();
//        test2();
//        test3();
        test4();
    }

    /**
     * 扩展ThreadPoolExecutor
     * 在ThreadPoolExecutor.Worker.runTask()
     */
    private static void test4() {
        MyThreadPoolExecutor executor = new MyThreadPoolExecutor(100, 200,
                0L, TimeUnit.SECONDS,
                new PriorityBlockingQueue<Runnable>());
        for (int i = 0; i < 10; i++) {
            executor.execute(new MyThread("Thread-" + i));
        }



    }

    /**
     * 优化线程池大小
     * Ncpu = CPU的数量
     * Ucpu = 目标CPU的使用率,0<=Ucpu<=1
     * W/C = 等待时间与计算时间的比率
     * Nthread = Ncpu* Ucpu*(1+W/C)
     */
    private static void test3() {
        // 获取CPU的数量
        System.out.println(Runtime.getRuntime().availableProcessors());//8
        System.out.println(8*0.5*0.5);
    }

    /**
     * 自定义 ThreadPoolExecutor
     * 1、int corePoolSize,线程池中的线程数量
       2、int maximumPoolSize,线程池中最大的线程数量
       3、long keepAliveTime,当线程池中的线程数，超过了 corePoolSize ，多余空闲线程的存货时间
       4、TimeUnit unit,keepAliveTime的单位时间
       5、BlockingQueue<Runnable> workQueue，任务队列，被提交但尚未执行的任务。存放 Runnable 对象
            5-1 :直接提交的队列（SynchronousQueue），没有容量，不保存任务，总是将任务提交给线程执行。通常需要设置很大的 maximumPoolSize的值
            5-2 :有界任务队列（ArrayBlockingQueue）
            5-3 :无界任务队列（LinkedBlockingQueue）
            5-4 :优先任务队列（PriorityBlockingQueue），控制任务的优先执行顺序，特殊的无界队列
       6、ThreadFactory threadFactory,线程工厂，用于创建线程，一般使用默认的即可
       7、RejectedExecutionHandler handler,拒绝策略，当任务太多来不及处理，如何拒绝
            7-1 ：DiscardOldestPolicy，丢弃最老的一个请求，也就是即将被执行的一个任务，并尝试再次提交当前任务
            7-2 ：AbortPolicy，直接抛出异常
            7-3 ：CallerRunsPolicy，只要线程池未关闭，改策略直接在调用者线程中，运行当前被丢弃的任务
            7-4 ：DiscardPolicy，丢弃无法处理的任务，不予任何处理
     */
    private static void test2() {

        // 一开始有大量的空闲线程，不需要等待队列。之后就会进入优先级等待队列
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 200,
                                        0L, TimeUnit.SECONDS,
                                            new PriorityBlockingQueue<Runnable>());//优先列队
        for (int i = 0; i < 1000; i++) {
            threadPoolExecutor.execute(new MyThread("ThreadPoolExecutorTest_" + Integer.toString(999-i)));
        }


    }

    /**
     * 所有的线程池都使用 ThreadPoolExecutor 这个类
     */
    private static void test1() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        ExecutorService executorService = Executors.newCachedThreadPool();

        ExecutorService executorService1 = Executors.newSingleThreadExecutor();

    }


}
