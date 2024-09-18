package demo;

import system.Sys;

public class SetenvDemo {
    public static void main(String[] args) {
        var jver = System.getProperty("java.version");
        Sys.echo(jver);
        Sys.setenv("AAA", "aaa");
        Sys.echo(System.getenv("AAA"));
    }
}
