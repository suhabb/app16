package com.example.app16.ui.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
Does the handling of data conversions in the format YYYY-MM-DD
 */

public class DateComponent {

    public static long getEpochSeconds(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = df.parse(date);
            long time = d.getTime() / 1000;
            return time;
        } catch (Exception _e) {
            return -1;
        }
    }

    public static long getEpochMilliseconds(String format, String date) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date d = df.parse(date);
            long time = d.getTime();
            return time;
        } catch (Exception _e) {
            return -1;
        }
    }

    public static long getTime() {
        Date d = new Date();
        return d.getTime();
    }

    public static String getEpochToDate(long epochSecs) {
        Date curDate = new Date(epochSecs * 10000);
        return (curDate.toString());
    }


}