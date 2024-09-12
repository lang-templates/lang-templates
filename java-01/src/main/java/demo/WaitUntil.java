package demo;

//import java.util.Calendar;
import org.joda.time.*;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import system.Sys;

class Cons {
    public static void waitUntil(DateTime dt) {
        var start = new DateTime();
        PeriodFormatter formatter = new PeriodFormatterBuilder()
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
            var now = new DateTime();
            Interval interval = null;
            try {
                interval = new Interval(now, dt);
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
            //if (diff <= 0) break;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
                ;
            }
        }
        System.err.print("\r");
        System.err.print("\033[1F\33[K");
        System.err.print("Waiting...done (");
        var elapsed = new Interval(start, new DateTime());
        System.err.print(elapsed.toPeriod().toString(formatter));
        System.err.print(").");
        System.err.println();
        System.err.flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            ;
        }
    }
}

public class WaitUntil {
    public static void main(String[] args) throws Exception {
        Sys.echo("hello");
        var now = new DateTime();
        //var dt = now.plusMinutes(2);
        var dt = now.plusSeconds(15);
        Sys.echo(now, "now");
        Sys.echo(dt, "now");
        Cons.waitUntil(dt);

        //var c1 = Calendar.getInstance();
        var dt1 = new DateTime();

        PeriodFormatter daysHoursMinutes = new PeriodFormatterBuilder()
                .appendDays()
                .appendSuffix(" day", " days")
                .appendSeparator(" and ")
                .appendMinutes()
                .appendSuffix(" minute", " minutes")
                .appendSeparator(" and ")
                .appendSeconds()
                .appendSuffix(" second", " seconds")
                .toFormatter();

        for (int i=10; i>=0; i--) {
            System.out.print("\r");
            System.out.print("\033[1F\33[K");
            System.out.flush();
            var dt2 = new DateTime();
            Interval interval1 = new Interval(dt1, dt2);
            System.out.print(interval1.toPeriod().toString(daysHoursMinutes));
            System.out.print(":");
            System.out.print(interval1.getEndMillis()-interval1.getStartMillis());
            //System.out.print(i);
            System.out.flush();
            Thread.sleep(1000);
        }
        System.out.println();
    }
}
