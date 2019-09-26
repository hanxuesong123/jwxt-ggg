package com.jwxt.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date beforeOneMonth(String str) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(str);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH,0);
        String year = instance.get(Calendar.YEAR) +"-";
        String month = instance.get(Calendar.MONTH) +"-";
        String day = instance.get(Calendar.DAY_OF_MONTH) +"";

        Date newDate = dateFormat.parse(year + month + day);
        return newDate;
    }


    public static Date afterOneMonth(String str) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(str);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH,2);
        String year = instance.get(Calendar.YEAR) +"-";
        String month = instance.get(Calendar.MONTH) +"-";
        String day = instance.get(Calendar.DAY_OF_MONTH) +"";

        Date newDate = dateFormat.parse(year + month + day);
        return newDate;
    }

}
