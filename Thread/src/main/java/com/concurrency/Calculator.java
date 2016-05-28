package com.concurrency;

public class Calculator implements Runnable {

    // 声明一个名为number 的private int为属性，然后实现构造函数并初始化其值。
    private int number;

    public Calculator(int number) {
        this.number = number;
    }

    // 实现run()方法. 这方法是给我们创建的线程执行指令，所以这个方法将计算数字乘法表。
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.printf("%s: %d * %d = %d\n", Thread.currentThread().getName(), number, i, i * number);
        }
    }

    // 现在, 实现程序的Main类。创建一个名为 Main的类并包含 main() 方法.
    public static class Main {
        public static void main(String[] args) {

            // 在 main() 方法内，创建一个迭代10次的for循环。
            // 在每次循环中，创建一个 Calculator 类的对象,  一个Thread类的对象,
            // 然后传递 Calculator 对象作为thread类构造函数的参数，
            // 最后调用线程对象的start() 方法。
            for (int i = 1; i <= 10; i++) {
                Calculator calculator = new Calculator(i);
                Thread thread = new Thread(calculator);
                thread.start();
            }

        }
    }

}