package com.lsy.jstorm.utils;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lisiyu on 2016/11/14.
 */
public class DateUtils {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
    private static Date returnDate = new Date();

    public static Date strToDate(String dateStr) throws ParseException {
        returnDate = sdf.parse(dateStr);
        return returnDate;
    }

    public static Date strToDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        returnDate = df.parse(dateStr);
        return returnDate;
    }

    public static String dateToStr(Date date){
        return sdf.format(date);
    }

    public static String dateToStr(Date date, String format){
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        return df.format(date);
    }

    public static Timestamp dateToTimestamp(Date date){
        return new Timestamp(date.getTime());
    }


}
