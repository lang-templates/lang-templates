package demo;

import system.Sys;

import java.util.Calendar;

//import java.util.Calendar;

class Sleeper {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            ;
        }
    }
    public static void waitOnConsole(java.util.Date date) {
        org.joda.time.DateTime dt = new org.joda.time.DateTime(date);
        waitOnConsole(dt, System.err);
    }
    public static void waitOnConsole(java.util.Date date, java.io.PrintStream stream) {
        org.joda.time.DateTime dt = new org.joda.time.DateTime(date);
        waitOnConsole(dt, stream);
    }
    public static void waitOnConsole(org.joda.time.DateTime dt) {
        waitOnConsole(dt, System.err);
    }
    public static void waitOnConsole(org.joda.time.DateTime dt, java.io.PrintStream stream) {
        var start = new org.joda.time.DateTime();
        var formatter = new org.joda.time.format.PeriodFormatterBuilder()
                .appendYears()
                .appendSuffix(" year", " years")
                .appendSeparator(", ")
                .appendDays()
                .appendSuffix(" day", " days")
                .appendSeparator(", ")
                .appendMinutes()
                .appendSuffix(" minute", " minutes")
                .appendSeparator(", ")
                .appendSeconds()
                .appendSuffix(" second", " seconds")
                .toFormatter();
        String msg = "";
        while(true) {
            var now = new org.joda.time.DateTime();
            org.joda.time.Interval interval = null;
            try {
                interval = new org.joda.time.Interval(now, dt);
            } catch(IllegalArgumentException ex) {
                break;
            }
            var sb = new StringBuilder();
            sb.append("Waiting...");
            sb.append(interval.toPeriod().toString(formatter));
            sb.append(" left.");
            String s = sb.toString();
            if (!s.equals(msg)) {
                stream.print("\r");
                stream.print("\033[1F\33[K");
                stream.print(s);
                stream.flush();
                msg = s;
            }
            Sleeper.sleep(100);
        }
        stream.print("\r");
        stream.print("\033[1F\33[K");
        stream.print("Waiting...done (");
        var elapsed = new org.joda.time.Interval(start, new org.joda.time.DateTime());
        stream.print(elapsed.toPeriod().toString(formatter));
        stream.print(").");
        stream.println();
        stream.flush();
        Sleeper.sleep(1000);
    }
}

public class WaitUntil {
    public static void main(String[] args) throws Exception {
        Sys.echo("hello");
        //var now = new org.joda.time.DateTime();
        //var dt = now.plusSeconds(15);
        var now = new java.util.Date();
        var calendar = java.util.Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.SECOND, 15);
        var dt = calendar.getTime();
        Sys.echo(now, "now");
        Sys.echo(dt, "now");
        Sleeper.waitOnConsole(dt);
        Sys.echo("Finished");
    }
}
