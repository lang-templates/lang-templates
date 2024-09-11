package demo;

import system.Sys;

public class WaitUntil {
    public static void main(String[] args) throws Exception {
        Sys.echo("hello");
        for (int i=10; i>=0; i--) {
            System.out.print("\r");
            System.out.print("\033[1F\33[K");
            System.out.flush();
            System.out.print(i);
            System.out.flush();
            Thread.sleep(1000);
        }
        System.out.println();
    }
}
