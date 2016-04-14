package com.sendgrid.labs.vertx.schedule.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TimeZone;

import com.sendgrid.labs.vertx.schedule.TimeOfWeek;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;

import com.sendgrid.labs.vertx.schedule.TimeOfWeek.DstAheadBehavior;
import com.sendgrid.labs.vertx.schedule.TimeOfWeek.DstBackBehavior;


public class SchedulerLogic {
    public SchedulerLogic(TimeZone tz, Date start, int weekMs, TimeOfWeek.DstAheadBehavior aheadBehavior, TimeOfWeek.DstBackBehavior backBehavior) {
        weekTracker = new WeekTracker(tz, start, weekMs);
        this.aheadBehavior = aheadBehavior;
        this.backBehavior = backBehavior;
        events = weekTracker.get();
    }

    public Date next() {
        for(;;) {
            if(events.size() < 1) {
                weekTracker.advance();
                events = weekTracker.get();
            }

            EventInfo e = events.pop();
            if(e.isDstAheadHour) {
                if(aheadBehavior == DstAheadBehavior.DST_AHEAD_NEXT_HOUR)
                    return e.date;
            } else if(e.isDstBackHour1) {
                if(backBehavior == DstBackBehavior.DST_BACK_FIRST_HOUR || backBehavior == DstBackBehavior.DST_BACK_BOTH_HOURS)
                    return e.date;
            } else if(e.isDstBackHour2) {
                if(backBehavior == DstBackBehavior.DST_BACK_SECOND_HOUR || backBehavior == DstBackBehavior.DST_BACK_BOTH_HOURS)
                    return e.date;
            } else {
                return e.date;
            }
        }

    }

    private WeekTracker weekTracker;
    private LinkedList<EventInfo> events;
    private TimeOfWeek.DstAheadBehavior aheadBehavior;
    private TimeOfWeek.DstBackBehavior backBehavior;
}

