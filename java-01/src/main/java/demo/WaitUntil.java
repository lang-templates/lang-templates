package demo;

//import java.util.Calendar;
import org.joda.time.*;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import system.Sys;

class Console {
    public static void hello() {
        Sys.echo("hello from Console");
    }
    public static void waitUntil(DateTime dt) {
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
        //Sys.echo(dt);
        while(true) {
            var now = new DateTime();
            Interval interval = null;
            try {
                interval = new Interval(now, dt);
            } catch(IllegalArgumentException ex) {
                break;
            }
            long diff = interval.getEndMillis() - interval.getStartMillis();
            System.out.print("\r");
            System.out.print("\033[1F\33[K");
            //System.out.flush();
            System.out.print(interval.toPeriod().toString(formatter));
            System.out.flush();
            if (diff <= 0) break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                ;
            }
        }
        System.out.println();
    }
}

public class WaitUntil {
    public static void main(String[] args) throws Exception {
        Sys.echo("hello");
        Console.hello();
        var now = new DateTime();
        var dt = now.plusMinutes(2);
        Sys.echo(now, "now");
        Sys.echo(dt, "now");
        Console.waitUntil(dt);

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
