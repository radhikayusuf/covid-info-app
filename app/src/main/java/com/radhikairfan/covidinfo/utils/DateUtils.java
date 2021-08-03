package com.radhikairfan.covidinfo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Radhika Yusuf Alifiansyah
 * on 02/Aug/2021
 **/
public class DateUtils {
    
    public static String FULL_24_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static String STANDARD_TIME_FORMAT = "yyyy-MM-dd";
    public static String STANDARD_FULL_MONTH_TIME_FORMAT = "dd MMMM yyyy";

    public static String dateToString(Date date, String format) {
        SimpleDateFormat fmtOut = new SimpleDateFormat(format, new Locale("id"));
        return fmtOut.format(date);
    }

    public static Date stringToDate(String dateOnString, String format)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, new Locale("id"));
        try {
            return dateFormat.parse(dateOnString);
        } catch (ParseException e) {
            return new Date();
        }
    }
    
    public static String reformatStringDate(String dateOnString, String before, String after) {
        try {
            Date date = stringToDate(dateOnString, before);
            return  dateToString(date, after);
        } catch (Exception e ) {
            return "";
        }
    }

    public static String getTodayDate() {
        String day = dateToString(new Date(), "dd");
        return day;
    }

    public static String getThisMonth() {
        String month = dateToString(new Date(), "MM");
        return month;
    }

    public static String getThisYear() {
        String year = dateToString(new Date(), "yyyy");
        return year;
    }
    
    public static long dateToLong(Date date) {
        return date.getTime();
    }
    
    public static Date longToDate(Long timemilis) {
        return new Date(timemilis);
    }
}
