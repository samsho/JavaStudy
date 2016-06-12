package com.optimize.code.thread.design.mw;

import java.util.Map;
import java.util.Queue;

/**
 * ClassName: Worker
 * Description:
 * Date: 2016/6/12 21:31
 *
 * @author SAM SHO
 * @version V1.0
 */
public class Worker implements Runnable {

    // 任务队列，用于获取子任务
    protected Queue<Object> workQueue;

    // 子任务处理处理结果集
    protected Map<String, Object> resultMap;

    public void setWorkQueue(Queue<Object> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    /**
     * 子任务处理逻辑，在子类中实现具体逻辑
     *
     * @param input
     * @return
     */
    protected Object handle(Object input) {
        return input;
    }

    public void run() {
        while (true) {
            // 获取子任务
            Object input = workQueue.poll();
            if (input == null) {
                break;
            }
            // 处理子任务
            Object result = handle(input);
            // 将处理结果写入结果集
            resultMap.put(Integer.toString(input.hashCode()), result);
        }

    }
}
