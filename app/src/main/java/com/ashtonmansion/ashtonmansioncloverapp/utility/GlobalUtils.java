package com.ashtonmansion.ashtonmansioncloverapp.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by paul on 8/20/2016.
 */
public class GlobalUtils {

    public static String formatDate(int month, int day, int year) {
        String formattedDate;
        DateFormat df;

        Date appt_date = new Date(year - 1900, month, day);
        df = new SimpleDateFormat("MM/dd/yyyy");
        formattedDate = df.format(appt_date);

        return formattedDate;
    }

    public static String formatTime(int hour, int minute) {
        String amOrPm;
        if (hour >= 12) {
            amOrPm = "PM";
        } else {
            amOrPm = "AM";
        }
        String formattedTime = "" + (hour - 12) + ":" + minute + " " + amOrPm;
        return formattedTime;
    }


}
