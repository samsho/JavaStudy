package com.tool.semaphone;

import java.util.concurrent.Semaphore;

/**
 * ClassName: Pool
 * Desc:
 * Date： 2016/11/25
 * Created：shaom
 */
public class Pool {


    private static final int MAX_AVAIABLE = 100;

    private final Semaphore available = new Semaphore(MAX_AVAIABLE, true);

    public Object getItem() throws InterruptedException {

        // 申请一个许可
        // 同时只能有100 个线程进入
        // 可用项，超过100 个则需要等待
        available.acquire();

        return getNextAvailableitem();
    }


    public void putItem(Object object) {
        if(markAsUnused(object)) {

            // 新增一个使用项，释放一个许可
            // 请求资源的线程呗激活
            available.release();
        }


    }
}
