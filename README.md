# vertx-scheduler

A module to implement scheduling on top of Vertx timers.



## Installation
* Clone this repository
* Inside the vertx-scheduler folder, from terminal type:
		gradlew install 
	
	* Ths will install any jars produced to the local Maven repository (.m2).
	And now add the dependency in your project
			<dependency>
				<groupId>com.sendgrid.labs</groupId>
				<artifactId>vertx-scheduler</artifactId>
				<version>0.3.1</version>
			</dependency>
* You can also set the createFatJar to true in the gradle.properties file and type:
		gradlew assemble
	* And outputs are created in build/libs.
Check the build.gradle file for more options.
	
	
## Use

### Create a new scheduler

    Scheduler scheduler = Scheduler.create(vertx);

### Create a time of week

    // Tuesday at 2:30 AM UTC
    TimeOfWeek time1 = TimeOfWeek.create(Day.TUE, 2, 30, 0, 0);

    // Tuesday at 2:30 AM localtime
    TimeOfWeek time2 = TimeOfWeek.create(TimeZone.getTimeZone("America/New_York"), Day.TUE, 2, 30, 0, 0);

    // Sunday at 01:01 AM UTC
    TimeOfWeek time3 = TimeOfWeek.create(60*60*1000 + 60*1000);

    // Sunday at 01:01 AM localtime
    TimeOfWeek time4 = TimeOfWeek.create(TimeZone.getTimeZone("America/New_York"), 60*60*1000 + 60*1000);

### Set a one shot timer on next occurrence of time

    Timer timer1 = scheduler.setTimer(time1, new Handler<Timer>() {
        public void handle(Timer t) {
            // ...
        }
    });

### Set a periodic timer to fire on every occurrence of time

    Timer timer2 = scheduler.setPeriodic(time2, new Handler<Timer>() {
        public void handle(Timer t) {
            // ...
        }
    });

### Cancel a timer

    scheduler.cancelTimer(timer1);

### Daylight Saving Time

Behavior on DST changes is configurable.  Default is to skip when time changes ahead and to run both when time changes back.

    TimeOfWeek time1 = TimeOfWeek.create(TimeZone.getTime("America/New_York", Day.TUE, 2, 30, 0, 0, DstAheadBehavior.DST_AHEAD_SKIP, DstBackBehavior.DST_BACK_BOTH_HOURS);

#### Time change ahead
The scheduler can either skip any events during the missing hour (DST_AHEAD_SKIP), or run them on the next hour (DST_AHEAD_NEXT_HOUR).

#### Time change back
The scheduler can callback on the first occurrence of the duplicated hour (DST_BACK_FIRST_HOUR), just the second (DST_BACK_SECOND_HOUR), or both (DST_BACK_BOTH_HOURS).

### Dispose of scheduler

    scheduler.stop();


