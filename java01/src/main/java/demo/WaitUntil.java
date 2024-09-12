package demo;

import system.Sys;
import system.Waiter;

public class WaitUntil {
    public static void main(String[] args) throws Exception {
        Sys.println("hello");
        //var now = new org.joda.time.DateTime();
        //var dt = now.plusSeconds(15);
        var now = new java.util.Date();
        var calendar = java.util.Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(java.util.Calendar.SECOND, 15);
        var dt = calendar.getTime();
        Sys.echo(now, "now");
        Sys.echo(dt, "now");
        Waiter.waitOnConsole(dt);
        Sys.println("Finished");
    }
}
