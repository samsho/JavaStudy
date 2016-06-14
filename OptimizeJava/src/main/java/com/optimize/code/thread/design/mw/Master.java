package com.optimize.code.thread.design.mw;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ClassName: Master
 * Description:
 * Date: 2016/6/12 21:23
 *
 * @author SAM SHO
 * @version V1.0
 */
public class Master {

    // 任务队列
    protected Queue<Object> workQueue = new ConcurrentLinkedQueue<Object>();
    // worker 线程队列
    protected Map<String, Thread> threadMap = new HashMap<String, Thread>();
    // 子任务处理处理结果集
    protected Map<String, Object> resultMap = new ConcurrentHashMap<String, Object>();


    /**
     * 是否所有的任务都结束了
     *
     * @return
     */
    public boolean isComplete() {
        for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            if (entry.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    // Master 的构造，需要一个 Worker 进行逻辑，和需要的Worker 进行数
    public Master(Worker worker, int countWorker) {
        worker.setWorkQueue(workQueue);
        worker.setResultMap(resultMap);
        for (int i = 0; i < countWorker; i++) {
            threadMap.put(Integer.toString(i), new Thread(worker, Integer.toString(i)));
        }
    }

    /**
     * 提交一个任务
     *
     * @param job
     */
    public void submit(Object job) {
        workQueue.add(job);
    }

    /**
     * 开始运行所有 Worker 进程，进行处理
     */
    public void execute() {
        for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            entry.getValue().start();
        }
    }


    public Map<String, Object> getResultMap() {
        return resultMap;
    }


}
