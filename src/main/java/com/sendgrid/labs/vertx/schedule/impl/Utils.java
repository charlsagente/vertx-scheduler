package com.sendgrid.labs.vertx.schedule.impl;

import com.sendgrid.labs.vertx.schedule.TimeOfWeek;
import org.joda.time.DateTime;

import java.util.Date;

public class Utils {

    static public int convertWeekTime(TimeOfWeek.Day day, int hour, int minute, int sec, int ms) {
        return days(day_num(day)) + hours(hour) + minutes(minute) + seconds(sec) + ms;
    }

    static public Long getMsUntilDate(Date date) {
        Date now = new Date();
        Long ret = date.getTime() - now.getTime();
        if(ret < 0)
            ret = 10L;
        return ret;
    }

    static public TimeOfWeek getNextEventTimeInMillis(DateTime c) {
        DateTime currentDateTime = new DateTime();
        long diffInMillis = c.getMillis() - currentDateTime.getMillis();
        while (diffInMillis < 0) {
            c = c.plusDays(1);
            System.out.println("To next day: " + c.toDate().toString());
            diffInMillis = c.getMillis() - currentDateTime.getMillis();
        }


        return TimeOfWeek.create((int) diffInMillis);
    }

    static private int day_num(TimeOfWeek.Day d) {
        switch(d) {
            case SUN:
                return 0;
            case MON:
                return 1;
            case TUE:
                return 2;
            case WED:
                return 3;
            case THU:
                return 4;
            case FRI:
                return 5;
            case SAT:
                return 6;
        }
        return 0;
    }

    static private int days(int d) { return d*hours(24); }
    static private int hours(int h) { return h*minutes(60); }
    static private int minutes(int m) { return m*seconds(60); }
    static private int seconds(int s) { return s*1000; }

}

