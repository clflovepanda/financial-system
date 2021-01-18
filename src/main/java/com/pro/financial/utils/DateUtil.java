package com.pro.financial.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static int getQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        switch (month) {
            case 0:
            case 1:
            case 2:
                return 1;
            case 3:
            case 4:
            case 5:
                return 2;
            case 6:
            case 7:
            case 8:
                return 3;
            case 9:
            case 10:
            case 11:
                return 4;
        }
        return 0;
    }

    public static Date getStartDateByQuarter(int year, int quater, int month) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        //只有年
        if (quater < 1 || quater > 4) {

            date =  format.parse(year+"-01-01 00:00:00");
            return date;
        }
        //有年 季度 月
        if (month > 0 && month < 32 && quater > 0 && quater < 5) {
            date =  format.parse(year + "-" + month + "-01" +" 00:00:00");
            return date;
        }
        switch (quater) {
            case 1:
                date =  format.parse(year+"-01-01" + "00:00:00");
                break;
            case 2:
                date =  format.parse(year+"-04-01" + "00:00:00");
                break;
            case 3:
                date =  format.parse(year+"-07-01" + "00:00:00");
                break;
            case 4:
                date =  format.parse(year+"-10-01" + "00:00:00");
                break;
        }
        return date;
    }
    public static Date getEndDateByQuarter(int year, int quater, int month) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        //只有年
        if (quater < 1 || quater > 4) {

            date =  format.parse(year+"-12-31 23:59:59");
            return date;
        }
        //有年 季度 月
        if (month > 0 && month < 32 && quater > 0 && quater < 5) {
            month = month+1;
            date =  format.parse(year + "-" + month + "-01" +" 00:00:00");
            return date;
        }
        switch (quater) {
            case 1:
                date =  format.parse(year+"-03-31" + "23:59:59");
                break;
            case 2:
                date =  format.parse(year+"-06-30" + "23:59:59");
                break;
            case 3:
                date =  format.parse(year+"-09-30" + "23:59:59");
                break;
            case 4:
                date =  format.parse(year+"-12-31" + "23:59:59");
                break;
        }
        return date;
    }
}
