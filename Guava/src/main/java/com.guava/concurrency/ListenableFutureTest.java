package com.guava.concurrency;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Guava Future
 */
public class ListenableFutureTest {

    public static void main(String[] args) throws Exception {
//        test1();
        test2();
    }

    static void test1() throws ExecutionException, InterruptedException {

        /** juc包的使用**/
        /*方式一：Future*/
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Callable<String>(){
            public String call() throws Exception {
                Thread.sleep(2000);
                return "hello";
            }
        });
        System.out.println(future.get());// hello

        /*方式二：FutureTask*/
        FutureTask<String> futureTask = new  FutureTask<String>(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(200);
                return "hello";
            }
        });
        executor.submit(futureTask);
        System.out.println(futureTask.get());// hello
    }

    /**
     * guava的使用
     */
    static void test2() throws ExecutionException, InterruptedException {

        /**guava的使用**/
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture listenableFuture = service.submit(new Callable() {
            public String call() {
                return "Hello ListenableFuture";
            }
        });

        listenableFuture.get();
        Futures.addCallback(listenableFuture, new FutureCallback() {
            // we want this handler to run immediately after we push the big red button!
            public void onSuccess(Object result) {
                System.out.println(result);
            }
            public void onFailure(Throwable thrown) {
            }
        });


        /**
         * 另外, 假如你是从 FutureTask转换而来的,
         * Guava 提供ListenableFutureTask.create(Callable<V>) 和ListenableFutureTask.create(Runnable, V).
         * 和 JDK不同的是, ListenableFutureTask 不能随意被继承（译者注：ListenableFutureTask中的done方法实现了调用listener的操作）。
         */
        ListenableFutureTask<String> listenableFutureTask =  ListenableFutureTask.create(new Callable<String>() {
            public String call() throws Exception {
                return "Guava ListenableFutureTask";
            }
        });
        service.submit(listenableFutureTask);
        listenableFutureTask.get();
        Futures.addCallback(listenableFutureTask, new FutureCallback<String>() {
            public void onSuccess(String result) {
                System.out.println(result);
            }
            public void onFailure(Throwable thrown) {
            }
        });
    }

    static void test3() {
        /*ListenableFuture rowKeyFuture = indexService.lookUp(query);
        AsyncFunction<RowKey, QueryResult> queryFunction =
                new AsyncFunction<RowKey, QueryResult>() {
                    public ListenableFuture apply(RowKey rowKey) {
                        return dataService.read(rowKey);
                    }
                };
        ListenableFuture queryFuture = Futures.transform(rowKeyFuture, queryFunction, queryExecutor);*/
    }
}
