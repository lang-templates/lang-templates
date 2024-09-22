package demo;

import java.nio.file.Path;
import java.nio.file.Paths;
import system.Sys;

public class UserHome {
    public static void main(String[] args) {
        Sys.echo(System.getProperty("user.home"), "user.home");
        Path p1 = Paths.get(System.getProperty("user.home"));
        Path p2 = Paths.get("photo/profile.jpg");
        Path p3 = p1.resolve(p2);
        Sys.echo(p3, "p3");
        Sys.echo(p3.toString(), "p3.toString()");
    }
}
