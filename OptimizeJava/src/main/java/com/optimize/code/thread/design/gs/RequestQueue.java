package com.optimize.code.thread.design.gs;

import java.util.LinkedList;

/**
 * ClassName: RequestQueue
 * Description:维护请求列表
 * Date: 2016/6/14 21:35
 *
 * @author SAM SHO
 * @version V1.0
 */
public class RequestQueue {

    private LinkedList<Request> queue = new LinkedList<Request>();

    public synchronized Request getRequest() {
        while (queue.size() == 0) {
            try {
                wait();//等待直到有新的 Request 加入
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return (Request) queue.remove();//返回 Request 队列中的第一个请求
    }

    public synchronized void addRequest(Request request) {
        queue.add(request);//加入新的 Request 请求
        notify();// 通知 getRequest() 方法
    }
}
