package demo;

import system.Sys;

public class SetenvDemo {
  public static void main(String[] args) {
    var jver = System.getProperty("java.version");
    Sys.echo(jver);
    int jver2 = Sys.getJavaVersionAsInt();
    Sys.echo(jver2, "jver2");
    Sys.setenv("AAA", "aaa");
    Sys.echo(System.getenv("AAA"));
  }
}
