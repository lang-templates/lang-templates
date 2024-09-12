package demo;

//import org.joda.time.*;
//import org.joda.time.format.PeriodFormatter;
//import org.joda.time.format.PeriodFormatterBuilder;
import system.Sys;

class Sleeper {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            ;
        }
    }
    public static void waitUntil(org.joda.time.DateTime dt) {
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
                System.err.print("\r");
                System.err.print("\033[1F\33[K");
                System.err.print(s);
                System.err.flush();
                msg = s;
            }
            Sleeper.sleep(100);
        }
        System.err.print("\r");
        System.err.print("\033[1F\33[K");
        System.err.print("Waiting...done (");
        var elapsed = new org.joda.time.Interval(start, new org.joda.time.DateTime());
        System.err.print(elapsed.toPeriod().toString(formatter));
        System.err.print(").");
        System.err.println();
        System.err.flush();
        Sleeper.sleep(1000);
    }
}

public class WaitUntil {
    public static void main(String[] args) throws Exception {
        Sys.echo("hello");
        var now = new org.joda.time.DateTime();
        var dt = now.plusSeconds(15);
        Sys.echo(now, "now");
        Sys.echo(dt, "now");
        Sleeper.waitUntil(dt);
        Sys.echo("Finished");
    }
}
