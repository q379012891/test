package com.blgroup.jkl.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.blgroup.jkl.bean.LogTrace;

/**
 * 日期工具类
 * 
 * @version 1.0
 * 
 * @createDate 2012-7-31
 * 
 * @modifyDate 2012-8-14
 */
public class DateTimeUtil {
    private static final LogTrace log = new LogTrace("DateTimeUtil");

    /** 日期格式 ** */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String DATE_PATTERN2 = "yyyyMMdd";

    /** 日期格式 ** */
    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    

    /**
     * 获得当前的系统时间
     * 
     * @return 当前的系统日期
     */
    public static Date getCurrentTime() {
        return new Date();
    }

    /**
     * 获得当前的系统日期，不带有时分秒
     * 
     * @return 当前的系统日期
     */
    public static Date getCurrentDate() {

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.set(Calendar.HOUR_OF_DAY, 0);
        // c.clear(Calendar.HOUR);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);

        date = c.getTime();
        return date;
    }

    /**
     * 得到当前系统日期,格式："yyyy-MM-dd"
     * 
     * @return 当前系统日期
     */
    public static String getFormatCurrentDate() {
        return format(getCurrentDate(), DATE_PATTERN);
    }

    /**
     * 得到当前系统日期,格式："yyyyMMdd"
     * 
     * @return
     */
    public static String getFormatCurrentDate2() {
        return format(getCurrentDate(), DATE_PATTERN2);
    }

    /**
     * 得到当前系统日期,格式："yyyy-MM-dd HH:mm:ss"
     * 
     * @return 当前系统日期
     */
    public static String getFormatCurrentTime() {
        return format(getCurrentTime(), TIME_PATTERN);
    }

    /**
     * 获取当前月份
     * 
     * @return 当前月份
     */
    public static String getCurrentMonth() {
        return getDateMonth(getCurrentDate());
    }

    /**
     * 得到指定日期的月份
     * 
     * @param date
     *            指定日期
     * @return 指定日期的月份
     */
    public static String getDateMonth(Date date) {

        SimpleDateFormat format1 = new SimpleDateFormat(DATE_PATTERN);
        format1.setLenient(false);
        String dateStr = format1.format(date);
        int begin = dateStr.indexOf('-') + 1;
        int end = dateStr.lastIndexOf('-');
        String month = dateStr.substring(begin, end);
        return month;
    }

    /**
     * 得到指定日期后若干天的日期
     * 
     * @param date
     *            指定日期
     * @param days
     *            天数
     * @return 指定天数后的日期
     */
    public static Date afterDaysSinceDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        date = c.getTime();
        return date;
    }

    /**
     * 判断两个Date是否在同一天
     * 
     * @param date1
     *            要比较的日期1
     * @param date2
     *            要比较的日期2
     * @return 是否同一天
     */
    public static boolean isTwoDatesInSameDay(Date date1, Date date2) {
        Date preDate1 = preDay(date1);
        Date nextDate1 = nextDay(date1);
        if (date2.after(preDate1) && date2.before(nextDate1)) {
            return true;
        }
        return false;
    }

    /**
     * 得到指定日期的下一天
     * 
     * @param date
     *            日期
     * @return 指定日期的下一天
     */
    public static Date nextDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        return date;
    }

    /**
     * 得到指定日期的前一天
     * 
     * @param date
     *            日期
     * @return 指定日期的前一天
     */
    public static Date preDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        date = c.getTime();
        return date;
    }

    /**
     * 得到当前月份的下一个月份
     * 
     * @param date
     *            日期
     * @return 当前月份的下一个月份
     */
    public static Date addMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        date = c.getTime();
        return date;
    }

    /**
     * 得到年份与月份
     * 
     * @param date
     *            日期
     * @return 年份与月份信息
     */
    public static String getYearMonth(Date date) {
        String yearMonthStr = format(date, DATE_PATTERN);
        int index = yearMonthStr.lastIndexOf('-');
        yearMonthStr = yearMonthStr.substring(0, index);
        return yearMonthStr;
    }

    /**
     * 得到当前月的最后一天
     * 
     * @param date
     *            日期
     * @return 最后一天日期
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        date = c.getTime();
        return date;
    }

    /**
     * 得到当前月的第一天
     * 
     * @param date
     *            日期
     * @return 当前月的第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        date = c.getTime();
        return date;
    }

    /**
     * 判断一个日期是否在指定的时间段内
     * 
     * @param start
     *            时间段的起始时间
     * @param end
     *            时间段的结束时间
     * @param date
     *            要判断的日期
     * @return 是否在指定的时间段内
     */
    public static boolean inTimeSegment(Date start, Date end, Date date) {
        start = preDay(start);
        end = nextDay(end);
        if (date.after(start) && date.before(end)) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前日期是否在指定的时间段内
     * 
     * @param start
     *            时间段开始时间
     * @param end
     *            时间段结束时间
     * @return 如果当前日期在指定时间段内，则为true，否则为false
     */
    public static boolean isCurrentDateInTimeSegment(Date start, Date end) {
        Date date = getCurrentDate();
        if (inTimeSegment(start, end, date)) {
            return true;
        }
        return false;
    }

    /**
     * 计算两个时间之间相隔秒数
     * 
     * @param start
     *            开始时间
     * @param end
     *            结束时间
     * @return 相隔秒数
     */
    public static int getIntervalSeconds(Date start, Date end) {
        // 分别得到两个时间的毫秒数
        long sl = start.getTime();
        long el = end.getTime();

        long ei = el - sl;
        return (int) (ei / 1000);
    }

    /**
     * 得到指定月份的天数
     * 
     * @param date
     *            日期
     * @return 天数
     */
    public static int daysInMonth(Date date) {
        Date start = getFirstDayOfMonth(date);
        Date end = getLastDayOfMonth(date);
        String startStr = format(start, "yyyyMMdd");
        String endStr = format(end, "yyyyMMdd");
        return Integer.parseInt(endStr) - Integer.parseInt(startStr) + 1;
    }

    /**
     * 比较日期大小
     * 
     * @param dt1
     *            要比较的日期
     * @param dt2
     *            要比较的日期
     * @return 0:相等，1：dt1大于dt2，-1：dt1小于dt2
     */
    public static int compareDate(Date dt1, Date dt2) {
        if (dt1.getTime() > dt2.getTime()) {
            return 1;
        } else if (dt1.getTime() < dt2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 比较日期大小
     * 
     * @param date1
     *            要比较的日期
     * @param date2
     *            要比较的日期
     * @return 0:相等，1：date1大于date2，-1：date1小于date2
     */
    public static int compareDate(String date1, String date2) {
        DateFormat df = new SimpleDateFormat(TIME_PATTERN);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            return compareDate(dt1, dt2);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    /**
     * 格式化日期
     * 
     * @param date 日期
     * @param formart 格式
     * @return 格式化后的日期
     * @throws ParseException
     */
    public static Date parseDate(String date, String formart) {
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 默认的日期格式
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 默认的日期时间格式
     */
    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 初始开始时间
     */
    public static final String INIT_START_TIME = "1000-01-01 00:00:00";

    /**
     * 初始截止时间
     */
    public static final String INIT_END_TIME = "9999-12-31 23:59:59";

    /**
     * 初始开始日期
     */
    public static final String INIT_START_DATE = "1000-01-01";

    /**
     * 初始截止日期
     */
    public static final String INIT_END_DATE = "9999-12-31";

    /**
     * 初始时间-Date类型
     */
    public static final Date DEFAULT_DATE_TIME = getInitStartTime();

    /**
     * 获得初始开始时间
     * @return 初始开始时间
     */
    public static Date getInitStartTime() {
        return DateTimeUtil.parseDate(INIT_START_TIME, DateTimeUtil.TIME_PATTERN);
    }

    /**
     * 获得初始截止时间
     * @return 初始截止时间
     */
    public static Date getInitEndTime() {
        return DateTimeUtil.parseDate(INIT_END_TIME, DateTimeUtil.TIME_PATTERN);
    }

    /**
     * 获得初始开始日期
     * @return 初始开始日期
     */
    public static Date getInitStartDate() {
        return DateTimeUtil.parseDate(INIT_START_DATE, DateTimeUtil.DATE_PATTERN);
    }

    /**
     * 获得初始截止日期
     * @return 初始截止日期
     */
    public static Date getInitEndDate() {
        return DateTimeUtil.parseDate(INIT_END_DATE, DateTimeUtil.DATE_PATTERN);
    }

    /**
     * 获得指定年月的第一天
     * @param month yyyyMM或者yyyy-MM
     * @return 指定年月的第一天
     */
    public static Date getFirstDayByMonth(String month) {
        Date firstDay = null;
        StringBuffer temp = null;
        if (null == month || "".equals(month)) {
            return null;
        } else if (month.length() == 6) {
            temp = new StringBuffer();
            temp.append(month.substring(0, 4));
            temp.append("-");
            temp.append(month.substring(4));
            temp.append("-01 00:00:00");
            firstDay = DateTimeUtil.parseDate(temp.toString(), DateTimeUtil.TIME_PATTERN);
        } else if (month.length() == 7) {
            temp = new StringBuffer();
            temp.append(month);
            temp.append("-01 00:00:00");
            firstDay = DateTimeUtil.parseDate(temp.toString(), DateTimeUtil.TIME_PATTERN);
        }
        return firstDay;
    }

    /**
     * 获得指定年月的最后一天
     * @param month yyyyMM或者yyyy-MM
     * @return 指定年月的最后一天
     */
    public static Date getLastDayByMonth(String month) {
        Date firstDay = getFirstDayByMonth(month);
        if (null == firstDay) {
            return null;
        }
        return DateTimeUtil.getLastDayOfMonth(firstDay);
    }

    /**
     * 取得指定时间的最开始时间(00:00:00)
     * @author changwen
     * @param date 指定时间
     * @return Date
     */
    public static Date getInitStartTime(Date date) {
        String temp = DateTimeUtil.format(date, DateTimeUtil.DATE_PATTERN);
        temp = temp + " 00:00:00";
        return DateTimeUtil.parseDate(temp, DateTimeUtil.TIME_PATTERN);
    }

    /**
     * 取得指定时间的最晚时间(23:59:59)
     * @author changwen
     * @param date 指定时间
     * @return Date
     */
    public static Date getInitEndTime(Date date) {
        String temp = DateTimeUtil.format(date, DateTimeUtil.DATE_PATTERN);
        temp = temp + " 23:59:59";
        return DateTimeUtil.parseDate(temp, DateTimeUtil.TIME_PATTERN);
    }

    /**
     * 取得指定时间的最开始时间(00:00:00)
     * @author changwen
     * @param date 指定时间(yyyy-MM-dd)
     * @return Date
     */
    public static Date getInitStartTime(String date) {
        return DateTimeUtil.parseDate(date + " 00:00:00", DateTimeUtil.TIME_PATTERN);
    }

    /**
     * 取得指定时间的最晚时间(23:59:59)
     * @author changwen
     * @param date 指定时间(yyyy-MM-dd)
     * @return Date
     */
    public static Date getInitEndTime(String date) {
        return DateTimeUtil.parseDate(date + " 23:59:59", DateTimeUtil.TIME_PATTERN);
    }

    /**
     * 将日期转化为指定格式的字符串
     * @author 苏斌
     * @param date 日期时间对象,若为空则返回""
     * @return str
     */
    public static String format(Date date) {
        String str = "";

        if (null != date) {
            SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
            str = formatter.format(date);
        }

        return str;
    }

    /**
     * 将日期转化为指定格式的字符串
     * @author 苏斌
     * @param date 日期时间对象,若为空则返回""
     * @param pattern 日期格式
     * @return str
     */
    public static String format(Date date, String pattern) {
        String str = "";

        if (null != date) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            str = formatter.format(date);
        }

        return str;
    }

    /**
     * 得到两个日期的间隔天数
     * @param start
     *            起始时间
     * @param end
     *            结束时间
     * @return 间隔天数
     */
    public static int getBetweenDays(Date start, Date end) {
        if (start.after(end)) {
            return -1;
        }

        Calendar startC = Calendar.getInstance();
        startC.setTime(start);
        startC.set(Calendar.HOUR_OF_DAY, 0);
        startC.clear(Calendar.MINUTE);
        startC.clear(Calendar.SECOND);
        startC.clear(Calendar.MILLISECOND);

        Calendar endC = Calendar.getInstance();
        endC.setTime(end);
        endC.set(Calendar.HOUR_OF_DAY, 0);
        endC.clear(Calendar.MINUTE);
        endC.clear(Calendar.SECOND);
        endC.clear(Calendar.MILLISECOND);
        endC.add(Calendar.DAY_OF_YEAR, 1);
        int days = 0;
        do {
            days++;
            startC.add(Calendar.DAY_OF_YEAR, 1);
        } while (startC.before(endC));
        return days;
    }

    /**
     * 得到当前月份的上一个月份+1天
     * 如：2012-03-31 -> 2012-03-01
     * 如：2012-05-20 -> 2012-04-21
     * @param date 日期(yyyy-MM-dd)
     * @return 当前月份的上一个月份+1天
     */
    public static Date preMonth(String date) {
        Date temp = DateTimeUtil.parseDate(date, DateTimeUtil.DATE_PATTERN);
        Calendar c = Calendar.getInstance();
        c.setTime(temp);
        c.add(Calendar.MONTH, -1);
        c.add(Calendar.DAY_OF_YEAR, 1);
        temp = c.getTime();
        return temp;
    }

    /**
     * 取得当前年份的第一天
     * @author yyc
     * @param year
     * @return
     */
    public static String getFirstDayByYear(String year) {
        return year + "-01-01";
    }

    /**
     * 取得当前年份的最后一天
     * @author yyc
     * @param year
     * @return
     */
    public static String getLastDayByYear(String year) {
        return year + "-12-31";
    }

    /**
     * 查询 N月前或后的日期
     * @author yyc
     * @param date
     * @param month
     * @return
     */
    public static Date getDateByMonth(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

}
