package com.blgroup.jkl.util;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * date工具类
 * @author jzm
 */
public class WebDateTimeUtil {

    /**
     * 获取当前月份的上一个月
     * @author chenxinquan
     * @return yyyy-MM
     */
    public static String getLastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return DateTimeUtil.getYearMonth(c.getTime());
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
     * 当前时间的前N个月的时间日期+1
     * @author chenxinquan
     * @param monthNum 几个月
     * @param date 日期
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static Date getBeforeNumMonthTime(Date date, int monthNum) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -monthNum);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    /**
     * 某时间的后day天
     * @author chenxinquan
     * @param dayNum 几天
     * @param date 日期
     * @return timeStr
     */
    public static Date getBeforeNumDayTime(Date date, int dayNum) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, dayNum);
        return c.getTime();
    }
    
    /**
     * 取得配置文件里的超时时间
     * @author yyc
     * @return
     */
    public static int getSessionTime() {
        ResourceBundle rb = ResourceBundle.getBundle("mall");
        return Integer.valueOf(rb.getString("session.redis.timeout"));
    }
    
    /**
     * 取得配置文件里的用户超时时间
     * @author yyc
     * @return
     */
    public static int getCookieTime() {
        ResourceBundle rb = ResourceBundle.getBundle("mall");
        return Integer.valueOf(rb.getString("user.cookie.timeout"));
    }
    
    /**
     * 取得配置文件里的最近浏览超时时间
     * @author yyc
     * @return
     */
    public static int getReviewCookieTime() {
        ResourceBundle rb = ResourceBundle.getBundle("mall");
        return Integer.valueOf(rb.getString("review.cookie.timeout"));
    }
}
