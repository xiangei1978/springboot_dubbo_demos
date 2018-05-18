package com.davidxl.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 */
public class DateUtil {
    public static final String ShortDateFormat = "yyyy-MM-dd";
    public static final String ShortCompactDateFormat = "yyyyMMdd";
    public static final String LongDateFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String LongCompactDateFormat = "yyyyMMddHHmmss";
    public static final String FullDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FullCompactDateFormat = "yyyyMMddHHmmssSSS";
    public static final String TimeFormat = "HH:mm:ss";
    public static final String FullTimeFormat = "HH:mm:ss.SSS";
    public static final String ShortDateFormatSlash = "yyyy/MM/dd";
    public static final String LongDateFormatSlash = "yyyy/MM/dd HH:mm:ss";

    private static Calendar getCalendar() {
        TimeZone tz = TimeZone.getDefault();
        return Calendar.getInstance(tz);
    }

    /**
     * 设置年份
     */
    public static Date setYear(Date date, int year) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获取年份
     */
    public static int getYear(Date date) {
        return dateToCalendar(date).get(Calendar.YEAR);
    }

    /**
     * 获取年份
     */
    public static int getYear() {
        return getYear(new Date());
    }

    /**
     * 设置月份
     */
    public static Date setMonth(Date date, int month) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getTime();
    }

    /**
     * 获取月份
     */
    public static int getMonth(Date date) {
        return dateToCalendar(date).get(Calendar.MONTH) + 1;
    }

    /**
     * 获取月份
     */
    public static int getMonth() {
        return getMonth(new Date());
    }

    /**
     * 设置日期
     */
    public static Date setDay(Date date, int day) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 获取日期
     */
    public static int getDay(Date date) {
        return dateToCalendar(date).get(Calendar.DATE);
    }

    /**
     * 获取日期
     */
    public static int getDay() {
        return getDay(new Date());
    }

    /**
     * 设置小时
     */
    public static Date setHour(Date date, int hour) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    /**
     * 获取小时
     */
    public static int getHour() {
        return getHour(new Date());
    }

    /**
     * 获取小时
     */
    public static int getHour(Date date) {
        return dateToCalendar(date).get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 设置分
     */
    public static Date setMinute(Date date, int minute) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 获取分
     */
    public static int getMinute(Date date) {
        return dateToCalendar(date).get(Calendar.MINUTE);
    }

    /**
     * 获取分
     */
    public static int getMinute() {
        return getMinute(new Date());
    }

    /**
     * 设置秒
     */
    public static Date setSecond(Date date, int second) {
        Calendar calendar = dateToCalendar(date);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 获取秒
     */
    public static int getSecond(Date date) {
        return dateToCalendar(date).get(Calendar.SECOND);
    }

    /**
     * 获取秒
     */
    public static int getSecond() {
        return getSecond(new Date());
    }

    /**
     * 格式化日期
     *
     * @param date    日期
     * @param pattern 格式
     * @return
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 格式化日期
     *
     * @param cal     日期
     * @param pattern 格式
     * @return
     */
    public static String format(Calendar cal, String pattern) {
        return DateFormatUtils.format(cal, pattern);
    }

    /**
     * 格式化时间戳
     *
     * @param time    时间戳
     * @param pattern 格式
     * @return 格式化文本
     */
    public static String format(Long time, String pattern) {
        long mills = String.valueOf(time).length() < 13 ? time * 1000 : time;
        return DateFormatUtils.format(mills, pattern);
    }

    /**
     * 解析日期字符串
     */
    public static Date parseDate(String date, String pattern) throws ParseException {
        return DateUtils.parseDate(date, pattern);
    }

    /**
     * 获取当前时间戳，秒
     *
     * @return 返回1970-1-1至今的时间戳，精确到秒
     */
    public static String getCurrentTime() {
        return String.valueOf((long) (System.currentTimeMillis() * 0.001));
    }

    /**
     * 将日期转换成秒数
     *
     * @param date 精确到秒的日期字符串
     */
    public static String convertToSeconds(String date, String pattern) throws ParseException {
        Date s = DateUtils.parseDate(date, pattern);
        return String.valueOf(s.getTime() / 1000);
    }

    /**
     * 日期格式转换
     */
    public static String convertPattern(String date, String patternOld, String patternNew) throws ParseException {
        Date d = DateUtils.parseDate(date, patternOld);
        return DateFormatUtils.format(d, patternNew);
    }

    /**
     * 获取前几个月或者下几个月的时间戳，秒
     */
    public static String addMonths(int months) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, months);
        return String.valueOf(cal.getTimeInMillis() / 1000);
    }

    /**
     * 加天
     */
    public static Date addDays(Date date, int days) {
        return DateUtils.addDays(date, days);
    }

    /**
     * 加年
     */
    public static Date addYears(Date date, int years) {
        return DateUtils.addYears(date, years);
    }

    /**
     * 加年
     */
    public static Date addYearsAsOracleDB(Date date, int years) {
        return addMonthAsOracleDB(date, years * 12);
    }

    /**
     * 比较2个时间按毫秒位相比相差多少天
     *
     * @return 得到一个天数的正整数  1.5天会被进位为2天
     */
    public static int diffCeilDays(Date date1, Date date2) {
        return (int) (Math.ceil(Math.abs(date1.getTime() * 1.0 - date2.getTime()) / 1000 / 24 / 60 / 60));
    }

    /**
     * 比较2个时间按毫秒位相比相差多少天
     *
     * @return 得到一个天数的正整数  1.5天会被舍为1天
     */
    public static int diffFloorDays(Date date1, Date date2) {
        return (int) (Math.floor(Math.abs(date1.getTime() * 1.0 - date2.getTime()) / 1000 / 24 / 60 / 60));
    }

    /**
     * 比较2个时间按日期位相比相差多少天
     * 例如：当天凌晨0点0分和前一天的最后一分相差一天
     *
     * @return
     */
    public static int diffDays(Date date1, Date date2) {
        return diffFloorDays(DateUtils.truncate(date1, Calendar.DATE), DateUtils.truncate(date2, Calendar.DATE));
    }

    /**
     * 传入一个日期获得所传日期的月的天数
     *
     * @param date 日期
     * @return
     */
    public static String getDayCountOfMonth(Date date) throws ParseException {
        Date nextMonthFirst = DateUtils.ceiling(date, Calendar.MONTH); //下月第一天
        Calendar cal = Calendar.getInstance();
        cal.setTime(nextMonthFirst);
        cal.add(Calendar.DAY_OF_MONTH, -1);  //当月最后一天
        return String.valueOf(DateUtils.getFragmentInDays(cal, Calendar.MONTH));
    }

    /**
     * 获取给定日期当天最后一秒钟的秒数
     */
    public static Date getLastSecondOfCurDay(Date date) {
        return DateUtils.addSeconds(DateUtils.ceiling(date, Calendar.MONTH), -1);
    }

    /**
     * 获取给定日期当天第一秒钟的秒数
     */
    public static Date getFirstSecondOfDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    /**
     * 本月第一天
     */
    public static Date getFirstDayOfTheMonth(Date date) {
        return DateUtils.truncate(date, Calendar.MONTH);
    }

    /**
     * 本月最后一天
     */
    public static Date getLastDayOfTheMonth(Date date) {
        return DateUtils.addDays(DateUtils.ceiling(date, Calendar.MONTH), -1);
    }

    /**
     * 前台页面显示的时间格式
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static String dateToSqlTime(String date) throws Exception {
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat(ShortDateFormat);
            Date s = dateformat.parse(date);
            return String.valueOf(s.getTime() / 1000L);
        } catch (ParseException e) {
            throw e;
        }
    }

    /**
     * 相对输入时间往后，往前计算日期
     * ：type='M' 月份往后,往前 num 个月
     * ：type='D' 天数份往后,往前 num 个天
     * ：type='Y' 年份份往后,往前 num 个年
     *
     * @param inputdate 输入的时间
     * @param type      类型：Y，M，D
     * @param num       相加，相减的数量
     * @return 处理后的时间
     * @Deprecated 因为使用了数据库的加月 加年处理，有别于JAVA默认方法，而方法名又没有体现这一差异，故不推荐使用，请使用明确的addMonthAsOracleDB()方法
     */
    @Deprecated
    public static Date getCalDate(Date inputdate, String type, int num) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(LongDateFormat);
            Calendar c = Calendar.getInstance();
            c.setTime(inputdate);
            int MaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            int day = c.get(Calendar.DATE);
            if ("D".equals(type)) {
                c.add(Calendar.DATE, num);
            } else if ("M".equals(type)) {
                c.add(Calendar.MONTH, num);
                if (MaxDay == day) {
                    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DATE));
                }
            } else if ("Y".equals(type)) {
                c.add(Calendar.YEAR, num);
                if (MaxDay == day) {
                    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DATE));
                }
            }
            String dayAfter = sdf.format(c.getTime());
            Date outdate = sdf.parse(dayAfter);
            return outdate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加天
     *
     * @param date
     * @param i
     */
    public static Date addDate(Date date, int i) {
        return DateUtils.addDays(date, i);
    }

    public static Date moDate(String date) throws ParseException {
        SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = sj.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 加月
     *
     * @param date
     * @param i
     */
    public static Date addMonth(Date date, int i) {
        return DateUtils.addMonths(date, i);
    }

    /**
     * 加月
     *
     * @param date
     * @param i
     */
    public static Date addMonthAsOracleDB(Date date, int i) {
        Date resultDate = addMonth(date, i);
        if (isLastDayOfTheMonth(date) && !isLastDayOfTheMonth(resultDate)) {
            resultDate = getLastDayOfTheMonth(resultDate);
        } else ;
        return resultDate;
    }

    /**
     * 获取Calendar（Date转Calendar）
     *
     * @param date
     * @return
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static boolean isLastDayOfTheMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE));
    }

    /**
     * 加N个自然月
     *
     * @param date
     * @param months
     * @return
     */
    public static Date addNaturalMonth(Date date, int months) {
        int day = getDay(date);
        Date date2 = addMonth(date, months);
        int day2 = getDay(getLastDayOfTheMonth(date2));
        int resultDay = day > day2 ? day2 : day - 1;
        if (0 != resultDay) {
            return setDay(date2, resultDay);
        } else {
            return getLastDayOfTheMonth(date);
        }
    }

    /**
     * 加N个自然年 (12*N个自然月)
     *
     * @param date
     * @param months
     * @return
     */
    public static Date addNaturalYear(Date date, int months) {
        return addMonth(date, months * 12);
    }

    /**
     * 指定日期加上天数后的日期
     *
     * @param num     为增加的天数
     * @param newDate 创建时间
     * @return
     * @throws ParseException
     */
    public static Date plusDay(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currdate = format.parse(newDate);
        System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        System.out.println("增加天数以后的日期：" + format.format(currdate));
        return currdate;
    }


    /**
     * 给时间加上几个小时
     *
     * @param date 当前时间 格式：yyyy-MM-dd HH:mm:ss
     * @param hour 需要加的时间
     * @return
     */
    public static String addDateMinut(Date date, int hour) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null)
            return "";
        System.out.println("front:" + format.format(date)); //显示输入的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制
        date = cal.getTime();
        System.out.println("after:" + format.format(date));  //显示更新后的日期
        cal = null;
        return format.format(date);

    }

    /**
     * 获取当前日期 格式：yyyy-MM-dd
     *
     * @return
     */
    public static Date getSimpleNowDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(format.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取自然日超时时间
     *
     * @return
     */
    public static Date getNaturalExpireDate(Integer validDays) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);         //获取年
        int month = calendar.get(Calendar.MONTH);       //获取月份
        int day = calendar.get(Calendar.DAY_OF_MONTH);  //获取当前天数
        calendar.set(year, month, day, 23, 59, 59);
        Date newData = calendar.getTime();
        return DateUtils.addDays(newData, validDays - 1);
    }
    /**
     * @Title: getTodayEndTime
     * @Description: 获取今天最晚的时间点返回
     * @return
     */
    public static Date getTodayEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }
}