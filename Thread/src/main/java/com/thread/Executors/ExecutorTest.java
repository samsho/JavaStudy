package com.thread.Executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by home on 2016/3/5.
 */
public class ExecutorTest {

    public static void main(String[] args) {
        /*
     * newFixedThreadPool()
     * 固定的线程池 创建线程池,只有3个,每次丢很多任务，但是只有三个线程一起处理
     *
     * newCachedThreadPool()
     * 缓存的线程池 有几个任务就可以有几个线程
     *
     * newSingleThreadExecutor()
     * 一个单一线程，但是当这个线程死后，会再出现一个新的线程：可以实现线程死掉后重新启动
     *
     */
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
//		ExecutorService threadPool = Executors.newCachedThreadPool();
//		ExecutorService threadPool = Executors.newSingleThreadExecutor();

        //一个池，6个任务
        for (int i = 0; i < 6; i++) {

            final int task = i;
            threadPool.execute(new Runnable() {
                public void run() {
                    for (int j = 1; j < 5; j++) {//每个任务单独循环处理5次
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName()
                                + " is loop off（循环） " + j + " , is for 任务  "+task);
                    }

                }

            });
        }

        System.out.println("all of 6 tasks（任务） have committed! ");

        threadPool.shutdown();//关闭线程
//		threadPool.shutdownNow();//立马死掉，即使还有任务

    }
}
