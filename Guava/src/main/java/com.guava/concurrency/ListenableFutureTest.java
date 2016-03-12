package com.guava.concurrency;

import com.google.common.util.concurrent.*;

import java.util.concurrent.*;

/**
 * Created by home
 * date 2016/3/12.
 */
public class ListenableFutureTest {

    public static void main(String[] args) throws Exception {
        test1();
    }

    static void test1() throws ExecutionException, InterruptedException {

        /** juc包的使用**/
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Callable<String>(){
            public String call() throws Exception {
                Thread.sleep(2000);
                return "hello";
            }
        });
        future.get();

        FutureTask<String> futureTask = new  FutureTask<String>(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(2000);
                return "hello";
            }
        });
        executor.submit(futureTask);
        futureTask.get();
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
                return "Guava ";
            }
        });
        service.submit(listenableFutureTask);
        listenableFutureTask.get();
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
