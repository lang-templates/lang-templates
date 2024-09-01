package app;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class Main {
    public static String argsList = "";
    public static void main(String[] args) throws Exception {
        Main.argsList = String.join(",", args);
        Path path = getApplicationPath(Main.class);
        System.out.println("path=" + path);
        Path dir = path.getParent();
        System.out.println("dir=" + dir);
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(System.getProperty("java.application.path"));
        System.out.println(System.getProperty("java.application.name"));
        System.out.println(System.getProperty("java.application.version"));
        System.out.println(System.getProperty("java.application.startup"));
        System.out.println("started!");
        //throw new Exception("test ex");
        app.MainG.main(args);
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
