package com.optimize.code.thread.excutor.defined;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: MyThreadPoolExecutor
 * Desc: 扩展ThreadPoolExecutor
 * 在ThreadPoolExecutor.Worker.runTask()
 * Date： 2016/11/21
 * Created：shaom
 */
public class MyThreadPoolExecutor  extends ThreadPoolExecutor{

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        System.out.println("线程执行开始前" + t.getId() +"_"+ t.getName()+ "_" + ((MyThread)r).getName());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        System.out.println("线程执行结束后 " + Thread.currentThread().getName());
        System.out.println("结束后 PoolSize " + + this.getPoolSize());
    }

    @Override
    protected void terminated() {
        System.out.println("线程执行~~~" );
    }


}
