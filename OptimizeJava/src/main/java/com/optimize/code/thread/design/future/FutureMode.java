package com.optimize.code.thread.design.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * ClassName: FutureMode
 * Description: 并行设计：Future
 * Date: 2016/6/12 21:11
 *
 * @author SAM SHO
 * @version V1.0
 */
public class FutureMode {


    public static void main(String[] args) throws Exception {
        FutureTask<String> futureTask = new FutureTask<String>(new RealData("a"));
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(futureTask);
        System.out.println("请求完毕");
        // 这里依然可以做一些额外的数据操作
        Thread.sleep(2000);
        // 如果此时 call() 方法没有执行完毕，依然会等待
        System.out.println("数据 = " + futureTask.get());
    }


    static class RealData implements Callable<String> {

        private String para;

        public RealData(String para) {
            this.para = para;
        }

        public String call() throws Exception {
            // 系统任务
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                sb.append(i);
                Thread.sleep(100);
            }
            return sb.toString();
        }
    }
}
