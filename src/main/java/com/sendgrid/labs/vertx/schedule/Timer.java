package com.sendgrid.labs.vertx.schedule;

import java.util.Date;

/**
 * Represents a timer created by the Scheduler
 */
public class Timer {
    public Date getNext() { return next; }

    public Date next = null;
}

