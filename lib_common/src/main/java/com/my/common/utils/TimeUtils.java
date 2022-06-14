package com.my.common.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils
{
    /**
     * 将long类型时间转换成指定的格式
     * @param timeMillis
     * @param pattern
     * @return
     */
    public static String formatTime(long timeMillis, String pattern)
    {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(timeMillis));
    }
    
    /**
     * 将long类型时间转换成年月日的形式
     * @param time
     * @return
     */
    public static String formatGeneralDate(long time)
    {
        return formatTime(time, "yyyy-MM-dd");
    }
	
	/**
     * 将long类型时间转换成年月日时分秒的形式
     * @param time
     * @return
     */
    public static String formatGeneralTime(long time)
    {
        return formatTime(time, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 按年月日,返回路径/文件的上次修改时间，若文件/路径不存在，返回空
     * @param path
     * @return
     */
    public static String getFileDate(String path)
    {
        File file = new File(path);
        if (file.exists())
        {
            long time = file.lastModified();
            return formatGeneralDate(time);
        }
        return "";
    }
    
    /**
     * 按年月日，返回当前时间
     * @param pattern
     * @return
     */
    public static String formatCurrentTime(String pattern){
        long time=System.currentTimeMillis();
        return formatTime(time, pattern);
    }
    
    /**
     * 将时间按格式还原成long类型
     * @param time
     * @param pattern
     * @return
     * @throws ParseException 
     */
    public static long getTimeMillis(String time, String pattern) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);  
        Date date = null;  
        date = formatter.parse(time);
        return date.getTime();
        
    }

    /**
     * 获取中文星期
     */

    /*获取星期几*/
    public static String getWeek() {
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "";
        }
    }

//    //获取前一天时间
//    public static String getBeforetDay(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE,-2);
//        Date data=calendar.getTime();
//        return formatGeneralDate(date.getTime());
//    }

    //获取昨天的日期字符串
    public static String getBeforeDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        Date date = calendar.getTime();
        return formatGeneralDate(date.getTime());
    }

    //获取今天日期字符串
    public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return formatGeneralDate(date.getTime());
    }

    //获取明天的日期字符串
    public static String getTomorrow(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,+1);
        Date date = calendar.getTime();
        return formatGeneralDate(date.getTime());
    }

    public static String getTimeStr(int secondCount) {
        int minite = secondCount / 60;
        int second = secondCount % 60;

        if (minite > 0) {
            return minite + "分" + second + "秒";
        } else {
            return second + "秒";
        }
    }

}
