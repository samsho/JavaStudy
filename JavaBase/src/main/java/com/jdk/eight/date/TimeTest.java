package com.jdk.eight.date;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

/**
 * ClassName: TimeTest
 * Description:
 * Date: 2016/7/7
 * Time: 13:51
 *
 * @author sm12652
 * @version V1.0.6
 */
public class TimeTest {


    /**
     * 时间线与Instant
     */
    public void instant() throws InterruptedException {
        //
        Instant start = Instant.now();
        System.out.println(start);//2017-02-15T05:26:48.119Z
        Thread.sleep(2000l);
        Instant end = Instant.now();
        System.out.println(end);//2017-02-15T05:26:50.193Z
        Duration between = Duration.between(start, end);
        System.out.println(between);//PT2.074S


        System.out.println("**********************************");
    }

    /**
     * 本地日期时间
     * 本地日期时间只有日期和/或当天的时间信息
     */
    public void localTime() {

        /*初始化*/
        LocalDate today = LocalDate.now();
        System.out.println(today);//2017-02-15

        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        System.out.println(now);//2017-02-15

        LocalDate birthDay = LocalDate.of(1989, 7, 16);//1989-07-16
        System.out.println(birthDay);

        LocalDate dob = LocalDate.of(1989, Month.JULY, 16);//1989-07-16
        System.out.println(dob);

        LocalDateTime dateTime = LocalDateTime.now();//2017-02-15T13:31:50.042
        System.out.println(dateTime);

        System.out.println(dateTime.toLocalDate());
        System.out.println(dateTime.toLocalTime());
        System.out.println("Chronology: " + dateTime.getChronology());

        System.out.println("*********************************************");

        /*LocalDate api*/
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);//2017-02-15
        System.out.println(localDate.getDayOfWeek());//WEDNESDAY
        System.out.println(localDate.getDayOfYear());//46，一年中的第几天
        System.out.println(localDate.getDayOfMonth());//15，一个月中的第几天
        System.out.println(localDate.getYear());//2017
        System.out.println(localDate.getMonthValue());//2

        /*简单计算*/
        System.out.println(localDate.minusDays(20));//2017-01-26
        System.out.println(localDate.minusMonths(20));//2015-06-15
        System.out.println(localDate.minusYears(20));//1997-02-15
        System.out.println(localDate.minusWeeks(20));//2016-09-28


        /*Month api*/
        Month month = localDate.getMonth();
        System.out.println(month);//FEBRUARY
        System.out.println(month.getValue());//2
        System.out.println(month.firstMonthOfQuarter().getValue());//1
        System.out.println(month.firstDayOfYear(false));//32,所在月的第一天是一年中的第几天

        System.out.println(month.minus(1).getValue());//1, 月份加法
        System.out.println(month.plus(1).getValue());//3,月份减法


        /*DayOfWeek api 也有一些计算*/
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        System.out.println(dayOfWeek);//WEDNESDAY
        System.out.println(dayOfWeek.plus(2));//FRIDAY
        System.out.println(dayOfWeek.minus(2));//MONDAY


    }

    /**
     * 日期Adjusters TemporalAdjusters
     * 有时候需要计算类似于“每个月的第一个星期二”这类的需求
     * 2017-02-21
     * 1989-07-10
     * 1
     */
    public void adjusters() {

        LocalDate today = LocalDate.now();
        System.out.println(today);//2017-02-15

        /*设置时间*/
        System.out.println(today.withYear(2018));//2018-02-15,设置年
        System.out.println(today.withMonth(3));//2017-03-15,设置月
        System.out.println(today.withDayOfYear(1));//2017-01-01，设置年日
        System.out.println(today.withDayOfMonth(20));//2017-02-20，设置月日

        System.out.println("**********************************************");

         /*一些 api 使用*/
        System.out.println(today.with(TemporalAdjusters.firstDayOfYear()));//2017-01-01,今年第一天
        System.out.println(today.with(TemporalAdjusters.lastDayOfYear()));//2017-12-31，今年最后一天

        System.out.println(today.with(TemporalAdjusters.firstDayOfMonth()));//2017-02-01,本月第一天
        System.out.println(today.with(TemporalAdjusters.lastDayOfMonth()));//2017-02-28，本月最后一天

        System.out.println(today.with(TemporalAdjusters.firstDayOfNextMonth()));//2017-03-01，下月第一天
        System.out.println(today.with(TemporalAdjusters.firstDayOfNextYear()));//2018-01-01，下年第一天

        System.out.println(today.with(TemporalAdjusters.firstInMonth(DayOfWeek.TUESDAY)));//2017-02-07，本月第一个周二
        System.out.println(today.with(TemporalAdjusters.lastInMonth(DayOfWeek.TUESDAY)));//2017-02-28，本月最后一个周二
        System.out.println(today.with(TemporalAdjusters.dayOfWeekInMonth(5, DayOfWeek.TUESDAY)));//2017-03-07, 本月第5个周二，会跨月

        System.out.println("*********************************************************");
        /*下一个周二*/
        /*next 与 nextOrSame 的区别 next会跨越计算，而nextOrSame不会*/
        today = today.with(TemporalAdjusters.lastInMonth(DayOfWeek.TUESDAY));// 2017-02-28，本月最后一个周二
        System.out.println(today.with(TemporalAdjusters.next(DayOfWeek.TUESDAY)));//2017-03-07,下一个周二，会跨月
        LocalDate nextTuesday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));// 下一个周二，不会跨月
        System.out.println(nextTuesday);//2017-02-28
        System.out.println(nextTuesday.getDayOfWeek());//TUESDAY

        /*上一个周二*/
        System.out.println(today.with(TemporalAdjusters.previous(DayOfWeek.TUESDAY)));
        System.out.println(today.with(TemporalAdjusters.previousOrSame(DayOfWeek.TUESDAY)));


        System.out.println("自定义 ************************************************************");
        /*下一个工作日, 2017-2-17 是周五*/
        LocalDate myDate = LocalDate.of(2017, 2, 17).with(new TemporalAdjuster() {
            @Override
            public Temporal adjustInto(Temporal temporal) {

                LocalDate localDate = (LocalDate) temporal;//拿到的是今天
                do {
                    localDate = localDate.plusDays(1);//加1天
                } while (localDate.getDayOfWeek().getValue() >= DayOfWeek.SATURDAY.getValue());
                return localDate;
            }
        });
        System.out.println(myDate);//2017-02-20

    }

    /**
     * 格式化与解析
     * DateTimeFormatter
     */
    public void formatter() {
        DateTimeFormatter formatter;
        /*date - > string*/
        LocalDateTime dateTime = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(dateTime.format(formatter));//2016-07-07 14:37
//
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dt = LocalDate.parse("2017-02-16", formatter);
        System.out.println(dt);

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dt2 = LocalDateTime.parse("2017-02-16 12:23:21", formatter);
        System.out.println(dt2);


        LocalDate now = LocalDate.now();
        System.out.println(LocalDateTime.of(now, LocalTime.MIN));


    }

    public void test() {

/*        Instant<->java.util.Date
        Date.from(instant)
        date.toInstant()

        ZonedDateTime<->java.util.GregorianCalendar
        GregorianCalendar.from(zonedDateTime)
        cal.toZonedDateTime()

        Instant<->java.sql.Timestamp
        Timestamp.from(instant)
        timestamp.toInstant()

        LocalDateTime<->java.sql.Timestamp
        Timestamp.valueOf(localDateTime)
        timestamp.toLocalDateTime()

        LocalDate<->java.sql.Date
        Date.valueOf(localDate)
        date.toLocalDate();

        Time.valueOf(localTime)
        time.toLocalTime()

        DateTimeFormatter<->java.text.DateFormat
        format.toFormat()
        None

        java.util.TimeZone<->ZoneId
        TimeZone.getTimeZone(id)
        timeZone.toZoneId()

        java.nio.file.attribute.FileTime<->Instant
        FileTime.from(instant)
        fileTime.toInstant()*/
    }


}
