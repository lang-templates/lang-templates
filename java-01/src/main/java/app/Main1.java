package app;

import system.Dynamic;
import system.Sys;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class Main1 {
    public static void main(String[] args) throws Exception {
        Path path = getApplicationPath(Main.class);
        Sys.echo(path, "path");
        Sys.echo(path.toString(), "path.toString()");
        Path file = Paths.get(path.getParent().toString() + "/assets/qiita-9ea0c8fd43b61b01a8da.json");
        Dynamic qiitaObj = null;
        String text = Files.readString(file, StandardCharsets.UTF_8);
        qiitaObj = Dynamic.fromJson(text);
        qiitaObj
                .remove("coediting")
                .remove("body")
                .remove("group")
                .remove("organization_url_name")
                .remove("private")
                .remove("rendered_body")
                .remove("team_membership")
        ;
        qiitaObj.put("user", qiitaObj.get("user").get("id"));
        String tags = "";
        for (int i = 0; i < qiitaObj.get("tags").size(); i++) {
            Sys.echo(qiitaObj.get("tags").getAt(i).get("name"));
            if (i > 0) tags += "|";
            tags += qiitaObj.get("tags").getAt(i).get("name").asString();
        }
        qiitaObj.put("tags", tags);
        Sys.echoJson(qiitaObj);
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
