package com.thread.future.example1;

import java.util.concurrent.Callable;

/**
 * Created by home on 2016/3/5.
 */
public class RealData implements Callable<String>{
    private String para;

    public RealData(String para) {
        this.para = para;
    }

    public String call() throws Exception {
        System.out.println("有请求，但是我很忙..........");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(para);
            Thread.sleep(1000);
        }
        return sb.toString();
    }

}
