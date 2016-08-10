/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author DUAN
 */
public class DateUtil {

    private static final String DEFUALT_FORMAT = "yyyy-MM-dd";
    private static final String DETAILED_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat defualtFormat;
    private static SimpleDateFormat detaledFormat;

    public DateUtil() {
    }

    /**
     * 将Date类型转化为字符串，格式为"yyyy-MM-dd"
     *
     * @param date Date类型
     * @return 日期字符串，如"2015-08-26"
     */
    public static String defaultDate2String(Date date) {
        defualtFormat = new SimpleDateFormat(DEFUALT_FORMAT);
        return defualtFormat.format(date);
    }

    /**
     * 将Date类型转化为字符串，格式为"yyyy-MM-dd HH:mm:ss"
     *
     * @param date Date类型
     * @return 日期字符串，如"2015-08-26"
     */
    public static String detailDate2String(Date date) {
        detaledFormat = new SimpleDateFormat(DETAILED_FORMAT);
        return detaledFormat.format(date);
    }

    /**
     * 日期年份的加减
     *
     * @param date Date类型
     * @param year int类型，值为正值表示加，负值未减
     * @return Date类型日期
     */
    public static Date addYear(Date date, int year) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.YEAR, year);

        return (Date) rightNow.getTime();
    }

    /**
     * 日期月份的加减
     *
     * @param date Date类型
     * @param month int类型，值为正值表示加，负值未减
     * @return Date类型日期
     */
    public static Date addMonth(Date date, int month) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MONTH, month);

        return rightNow.getTime();
    }

    /**
     * 日期天数的加减
     *
     * @param date Date类型
     * @param day int类型，值为正值表示加，负值未减
     * @return Date类型日期
     */
    public static Date addDay(Date date, int day) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_YEAR, day);

        return rightNow.getTime();
    }

    /**
     * 日期小时的加减
     *
     * @param date Date类型
     * @param hour int类型，值为正值表示加，负值未减
     * @return Date类型日期
     */
    public static Date addHour(Date date, int hour) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.HOUR_OF_DAY, hour);

        return rightNow.getTime();
    }

    /**
     * 日期分钟数的加减
     *
     * @param date Date类型
     * @param minute int类型，值为正值表示加，负值未减
     * @return Date类型日期
     */
    public static Date addMinute(Date date, int minute) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MINUTE, minute);

        return rightNow.getTime();
    }

    /**
     * 日期秒数的加减
     *
     * @param date Date类型
     * @param second int类型，值为正值表示加，负值未减
     * @return Date类型日期
     */
    public static Date addSecond(Date date, int second) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.SECOND, second);

        return rightNow.getTime();
    }

    /**
     * 字符串转日期Date类型，格式："yyyy-MM-dd"
     *
     * @param str 时间字符串
     * @return Date类型日期
     * @throws ParseException 解析格式异常
     */
    public static Date parseDeFaultDate(String str) throws ParseException {
        defualtFormat = new SimpleDateFormat(DEFUALT_FORMAT);
        return defualtFormat.parse(str);
    }

    /**
     * 字符串转日期Date类型，格式："yyyy-MM-dd HH:mm:ss"
     *
     * @param str 时间字符串
     * @return Date类型日期
     * @throws ParseException 解析格式异常
     */
    public static Date parseDetailDate(String str) throws ParseException {
        detaledFormat = new SimpleDateFormat(DETAILED_FORMAT);
        return detaledFormat.parse(str);
    }

    /**
     * 获取当前日期
     *
     * @return Date类型
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前"字符串类型"的日期，格式："yyyy-MM-dd"
     *
     * @return String类型字符串
     */
    public static String getDefaultNowDateString() {
        return defaultDate2String(new Date());
    }

    /**
     * 获取当前"字符串类型"的日期，格式："yyyy-MM-dd HH:mm:ss"
     *
     * @return String类型字符串
     */
    public static String getDetailNowDateString() {
        return detailDate2String(new Date());
    }
}
