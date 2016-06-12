package com.thread.future.example1;

import java.util.concurrent.*;

/**
 * Created by home
 * date 2016/3/5.
 */
public class main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        sampleSubmit();
        batch();
    }

    private static void batch() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        LinkedBlockingQueue<Future<String>> completionQueue = new LinkedBlockingQueue<Future<String>>(2);
        ExecutorCompletionService<String> eCService = new ExecutorCompletionService<String>(executorService, completionQueue);
        eCService.submit(new RealData("a1"));
        eCService.submit(new RealData("a2"));
        System.out.println(completionQueue.take().get());
        System.out.println(completionQueue.take().get());
        executorService.shutdown();
    }

    private static void sampleSubmit() throws InterruptedException, ExecutionException {
        FutureTask<String> future = new  FutureTask<String>(new RealData("design"));
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(future);

        //干其他事情去吧..........
        System.out.println("干其他事情去吧..........");
        System.out.println("返回的数据： " + future.get());
        executorService.shutdown();
    }
}
