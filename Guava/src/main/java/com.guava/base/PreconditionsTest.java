package com.guava.base;

import com.google.common.base.Preconditions;

/**
 * Created by home
 * date 2016/3/9.
 */
public class PreconditionsTest {

    public static void main(String[] args) {
//        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
    }

    static void test1() {

        int a = 9;
        int b = 8;
        boolean exp1 = a > b;
        boolean exp2 = b > a;

        //检查boolean是否为true，用来检查传递给方法的参数。	IllegalArgumentException
        Preconditions.checkArgument(exp1);
        Preconditions.checkArgument(exp1, "打印的错误信息");
        Preconditions.checkArgument(exp2, "打印的错误信息： %s", "【嵌入信息】", "[信息2]");

    }

    static void test2() {
        String str = "abc";
        String str2 = null;
        //检查value是否为null，该方法直接返回value，因此可以内嵌使用checkNotNull。	NullPointerException
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(str, "打印的错误信息");
        Preconditions.checkNotNull(str2, "打印的错误信息： %s", "【嵌入信息】", "[信息2]");


    }

    static void test3() {
//用来检查对象的某些状态。	IllegalStateException
//        Preconditions.checkState(boolean);


    }

    static void test4() {
        //检查index作为索引值对某个列表、字符串或数组是否有效。index>=0 && index<size *	IndexOutOfBoundsException
//        Preconditions.checkElementIndex(int index, int size);


    }

    static void test5() {
//检查index作为位置值对某个列表、字符串或数组是否有效。index>=0 && index<=size *	IndexOutOfBoundsException
//        Preconditions.checkPositionIndex(int index, int size);


    }

    static void test6() {
        //检查[start, end]表示的位置范围对某个列表、字符串或数组是否有效*	IndexOutOfBoundsException
//        Preconditions.checkPositionIndexes(int start, int end, int size);
    }

}
