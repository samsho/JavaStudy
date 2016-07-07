package com.jdk.eight.lambda;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * ClassName: LambdaTest
 * Description:
 * Date: 2016/7/7
 * Time: 15:02
 *
 * @author sm12652
 * @version V1.0.6
 */
public class LambdaTest {

    Lambda lambda;
    @Test
    public void before() {
        lambda = new Lambda();
    }

    @Test
    public void testInnerClass() throws Exception {
        lambda.innerClass();
    }
}