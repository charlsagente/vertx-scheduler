package com.sendgrid.labs.vertx.schedule.integration;

import com.sendgrid.labs.vertx.schedule.Scheduler;
import com.sendgrid.labs.vertx.schedule.Timer;
import com.sendgrid.labs.vertx.schedule.impl.Utils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.java.platform.Verticle;
import org.vertx.testtools.TestVerticle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Carlos Perez on 14/04/2016.
 */
public class TestVertxScheduler extends TestVerticle {
    public static final int MS_IN_SECOND = 1000;
    public static final int MS_IN_MINUTE = 60*MS_IN_SECOND;
    public static final int MS_IN_HOUR = 60*MS_IN_MINUTE;
    public static final int MS_IN_DAY = 24*MS_IN_HOUR;

    @Test
    public void testTimer(){
        Scheduler scheduler = Scheduler.create(vertx);
        final ArrayList<DateTime> Hours = new ArrayList<DateTime>();
        Hours.add(new DateTime().withTime(12, 01, 0, 0));
        Hours.add(new DateTime().withTime(12, 02, 00, 0));
        /*
        vertx.setPeriodic(MS_IN_MINUTE, new Handler<Long>() { //Por default, cada 24 horas (a partir de cuando se hace el deploy) se va a invocar la función setScheduler y se van a reprogramar los eventos.
            public void handle(Long timerID) {
                System.out.println("Every minute");
            }
        });*/
        try {
            for (int i = 0; i <= Hours.size() - 1; i++) {
                final int finalI = i;
                scheduler.setTimer(Utils.getNextEventTimeInMillis(Hours.get(i)), new Handler<Timer>() { //con este método se agenda la siguiente fecha y el engine de la librería se encarga de encolarlo en la lista de eventos.
                    public void handle(Timer t) {
                        closeEnough(Hours.get(finalI).toCalendar(new Locale("America/New_York")), Calendar.getInstance());
                        guaranteedChecker(); //Cada que se cumple la hora programada, se invoca esta función.
                    }
                });
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private boolean closeEnough(Calendar target, Calendar actual) {

        System.out.println("Checking that " + target.getTime().toString() + " and " + actual.getTime().toString() + " are very close");
        Calendar end = (Calendar) target.clone();
        end.add(Calendar.SECOND, 1);
        return actual.after(target) && actual.before(end);
    }

    private void guaranteedChecker() {
        //GuaranteedPaypalCheck.checkDates();
        System.out.println("Business logic");

    }
}
