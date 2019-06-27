package com.tszj.chain.sdk.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 是否本周
     * @param time
     */
    public static boolean isThisWeek(long time)
    {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if(paramWeek==currentWeek){
            return true;
        }
        return false;
    }

    /**
     * 是否本天
     * @param time
     */
    public static boolean isToday(long time)
    {
        return isThisTime(time,"yyyy-MM-dd");
    }

    /**
     * 是否本月
     * @param time
     */
    public static boolean isThisMonth(long time)
    {
        return isThisTime(time,"yyyy-MM");
    }
    private static boolean isThisTime(long time,String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if(param.equals(now)){
            return true;
        }
        return false;
    }
}