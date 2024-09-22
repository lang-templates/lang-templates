package app;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import system.Sys;

public class Main {
    public static String argsList = "";

    public static void main(String[] args) throws Exception {
        Main.argsList = String.join(",", args);
        Path path = getApplicationPath(Main.class);
        // System.out.println("path=" + path);
        Sys.echo(path, "path(パス)");
        Sys.echo(path.toString(), "path.toString()");
        Path dir = path.getParent();
        System.out.println("dir=" + dir);
        Sys.echo(System.getProperty("file.encoding"), "file.encoding");
        Sys.echo(System.getProperty("java.application.path"), "java.application.path");
        Sys.echo(System.getProperty("java.application.name"), "java.application.name");
        Sys.echo(System.getProperty("java.application.version"), "java.application.version");
        Sys.echo(System.getProperty("java.application.startup"), "java.application.startup");
        System.out.println("started!");
        // throw new Exception("test ex");
        MainG.main(args);
    }

    public static Path getApplicationPath(Class<?> cls) throws URISyntaxException {
        ProtectionDomain pd = cls.getProtectionDomain();
        CodeSource cs = pd.getCodeSource();
        URL location = cs.getLocation();
        URI uri = location.toURI();
        Path path = Paths.get(uri);
        return path;
    }
}
