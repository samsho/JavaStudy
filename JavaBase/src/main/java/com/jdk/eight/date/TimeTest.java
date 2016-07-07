package com.jdk.eight.date;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.util.Date;

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
    public void instant() {
        //
        Instant start = Instant.now();//2016-07-07T05:56:28.315Z
        System.out.println("..........do something");
        Instant end = Instant.now();
        Duration between = Duration.between(start, end);
        System.out.println(between);
    }

    /**
     * 本地日期时间
     * 本地日期时间只有日期和/或当天的时间信息
     */
    public void localTime() {

        LocalDate today = LocalDate.now();//2016-07-07
        System.out.println(today);

        LocalDate birthDay = LocalDate.of(1989, 7, 16);//1989-07-16
        System.out.println(birthDay);

        LocalDate dob = LocalDate.of(1989, Month.JULY, 16);//1989-07-16
        System.out.println(dob);

        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);
    }

    /**
     * 日期Adjusters TemporalAdjusters
     * 有时候需要计算类似于“每个月的第一个星期二”这类的需求
     */
    public void adjusters() {
        LocalDate today = LocalDate.of(1989, 7, 8);

        TemporalAdjuster NEXT_WORKDAY = w -> {
            LocalDate result = (LocalDate) w;
            do {
                result = result.plusDays(1);
            } while (result.getDayOfWeek().getValue() >= 6);
            return result;
        };
        LocalDate backToWork = today.with(NEXT_WORKDAY);
        System.out.println(backToWork);//2016-07-10
        System.out.println(backToWork.getDayOfWeek().getValue());//1

    }

    /**
     * 格式化与解析
     * DateTimeFormatter
     */
    public void formatter() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm");
        System.out.println(dateTime.format(formatter));//星期四 2016-07-07 14:37
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
