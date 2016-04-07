package com.jdk.base.throwtest;

/**
 * ClassName: TestException2
 * Description:
 * Date: 2016/3/25 14:38
 *
 * @author sm12652
 * @version V1.0
 */
public class TestException2 {

    public static void main(String[] args) {
//        test();
//        test1();
//        test2();
//        test3();

        try {
            test4();
        } catch (NegativeArraySizeException e) {
            System.out.println("处理方法中抛出的异常");
        }


    }


    static void test () {
        int a = 6;
        int b = 0;
        System.out.println("a/b的值是：" + a / b);//这边报错 java.lang.ArithmeticException: / by zero
        System.out.println("程序正常结束。");//不会执行，不打印
    }

    static void test1 () {
        int a = 6;
        int b = 0;
        try { // try监控区域
            System.out.println("a/b的值是：" + a / b);
        }catch (ArithmeticException e) { // catch捕捉异常
            System.out.println("程序出现异常，变量b不能为0。");//捕获异常，执行
        }
        System.out.println("程序正常结束。");//程序正常执行，打印
    }

    static void test2 () {
        int a = 6;
        int b = 0;
        try { // try监控区域
            if (b == 0) throw new ArithmeticException(); // 通过throw语句抛出异常
            System.out.println("a/b的值是：" + a / b);
        } catch (ArithmeticException e) { // catch捕捉异常
            System.out.println("程序出现异常，变量b不能为0。");//捕获异常，执行
        }
        System.out.println("程序正常结束。");//程序正常执行，打印
    }

    /**
     * finally
     */
    static void test3() {
        int i = 0;
        String greetings[] = { " Hello world !", " Hello World !! ", " HELLO WORLD !!!" };
        while (i < 4) {
            try {
                // 特别注意循环控制变量i的设计，避免造成无限循环
                System.out.println(greetings[i++]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("数组下标越界异常");
            } finally {
                System.out.println("--------------------------");//不管有没有异常，都会执行
            }
        }
    }

    /**
     * 抛出异常
     * @throws NegativeArraySizeException
     */
    static void test4() throws NegativeArraySizeException {
        // 定义方法并抛出NegativeArraySizeException异常
        int[] arr = new int[-3]; // 创建数组，会有异常
        System.out.println("这边不会正常执行了");
    }


}
