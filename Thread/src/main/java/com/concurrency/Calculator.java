package com.concurrency;

public class Calculator implements Runnable {

    // ����һ����Ϊnumber ��private intΪ���ԣ�Ȼ��ʵ�ֹ��캯������ʼ����ֵ��
    private int number;

    public Calculator(int number) {
        this.number = number;
    }

    // ʵ��run()����. �ⷽ���Ǹ����Ǵ������߳�ִ��ָ���������������������ֳ˷���
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.printf("%s: %d * %d = %d\n", Thread.currentThread().getName(), number, i, i * number);
        }
    }

    // ����, ʵ�ֳ����Main�ࡣ����һ����Ϊ Main���ಢ���� main() ����.
    public static class Main {
        public static void main(String[] args) {

            // �� main() �����ڣ�����һ������10�ε�forѭ����
            // ��ÿ��ѭ���У�����һ�� Calculator ��Ķ���,  һ��Thread��Ķ���,
            // Ȼ�󴫵� Calculator ������Ϊthread�๹�캯���Ĳ�����
            // �������̶߳����start() ������
            for (int i = 1; i <= 10; i++) {
                Calculator calculator = new Calculator(i);
                Thread thread = new Thread(calculator);
                thread.start();
            }

        }
    }

}