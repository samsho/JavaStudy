package com.optimize.code.thread.design.gs;

/**
 * ClassName: GSMode
 * Description:并发模式：Guarded Suspension（保护暂停）
 * Date: 2016/6/14 21:44
 *
 * @author SAM SHO
 * @version V1.0
 */
public class GSMode {

    public static void main(String[] args) {
        RequestQueue queue = new RequestQueue();

        for (int i = 0; i <10 ; i++) {
            new ServerThread("ServerTread#"+ i, queue).start();
        }

        for (int i = 0; i <10 ; i++) {
            new ClientThread("ClientTread#"+ i, queue).start();
        }
    }


    static class ServerThread extends Thread {
        private RequestQueue requestQueue;

        public ServerThread(String name, RequestQueue requestQueue) {
            super(name);
            this.requestQueue = requestQueue;
        }

        @Override
        public void run() {

            while (true) {
                final Request request = requestQueue.getRequest();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName()
                        + " handle " + request);
            }
        }
    }

    static class ClientThread extends Thread {
        private RequestQueue requestQueue;

        public ClientThread(String name, RequestQueue requestQueue) {
            super(name);
            this.requestQueue = requestQueue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Request request = new Request("RequestId" + i + "ThreadName "
                        + Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " requests "
                        + request);

                requestQueue.addRequest(request);

                try {
                    Thread.sleep(10);//Client 请求速度大于 server的速度
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ClientThread Name is :" + Thread.currentThread().getName());
            }
            System.out.println(Thread.currentThread().getName() + " Request end");

        }
    }
}
