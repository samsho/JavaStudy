package com.jdk.eight.date;

import org.junit.Before;
import org.junit.Test;

/**
 * ClassName: TimeTestTest
 * Description:
 * Date: 2016/7/7
 * Time: 13:54
 *
 * @author sm12652
 * @version V1.0.6
 */
public class TimeTestTest {
    TimeTest time;

    @Before
    public void before() {
        time = new TimeTest();
    }

    @Test
    public void testInstant() throws Exception {
        time.instant();
    }

    @Test
    public void testLocalTime() throws Exception {
        time.localTime();
    }

    @Test
    public void testAdjusters() throws Exception {
        time.adjusters();
    }

    @Test
    public void testFormatter() throws Exception {
        time.formatter();
    }
}