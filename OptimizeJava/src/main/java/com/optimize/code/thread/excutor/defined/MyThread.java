package com.optimize.code.thread.excutor.defined;

/**
 * ClassName: MyThread
 * Desc: 有限队列的自定义线程
 * Date： 2016/11/21
 * Created：shaom
 */
public class MyThread implements Runnable, Comparable<MyThread> {

    private String name;

    public MyThread() {
    }

    public MyThread(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void run() {
        try {
            Thread.sleep(100);//模拟工作
            System.out.println(name + " ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    public int compareTo(MyThread o) {// 比较优先级

        int me = Integer.parseInt(this.name.split("_")[1]);//线程名称中标注的优先级
        int other = Integer.parseInt(o.name.split("_")[1]);

        if (me > other) {
            return 1;
        } else if (me < other) {
            return -1;
        } else {
            return 0;
        }
    }
}
