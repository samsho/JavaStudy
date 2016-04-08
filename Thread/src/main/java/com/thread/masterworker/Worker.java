package com.thread.masterworker;

/**
 * ClassName: Worker
 * Description:
 * Date: 2016/4/7 17:46
 *
 * @author sm12652
 * @version V1.0
 */
public class Worker implements Runnable {

    protected Queue<Object> workQueue;
    protected Map<String, Object> resultMap;

    public void setWorkQueue(Queue<Object> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }


    public Object handle(Object input) {
        return input
    }

    @Override
    public void run() {
        while(true) {
            workQueue.poll();

        }

    }
}
