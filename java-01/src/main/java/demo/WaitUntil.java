package demo;

import java.util.Calendar;
import org.joda.time.*;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import system.Sys;

public class WaitUntil {
    public static void main(String[] args) throws Exception {
        Sys.echo("hello");

        var c1 = Calendar.getInstance();
        var dt1 = new DateTime();
        //Thread.sleep(3000);

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
