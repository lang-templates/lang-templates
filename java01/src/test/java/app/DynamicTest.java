package app;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import system.*;

import org.bson.BsonDocument;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.Date;

class DynamicTest {

    @Test
    void test() throws Exception {
        Sys.println("ハロー©");
        Dynamic list = Dynamic.newList(new Object[]{11, "abc", null, 12L, new Date()});
        Sys.echo(list, "list");
        Sys.echo(list.getAt(0), "list.getAt(0)");
        Sys.echo(list.getAt(0).asInt(), "list.getAt(0).asInt()");
        assertEquals(11, list.getAt(0).asInt());
        Sys.echo(list.getAt(0).asLong());
        Sys.echo(list.getAt(0).asDouble());
        Sys.echoJson(list.getAt(3).asLong());
        Sys.echo(list.getAt(2) == null);
        for (int i=0; i<list.size(); i++) {
            Sys.echoJson(list.getAt(i), "" + i);
        }
        //var listBson = Dynamic.toBsonValue(list);
        var listBson = list.toBsonValue();
        Sys.echo(listBson, "listBson");
        var list2 = Dynamic.fromBsonValue(listBson);
        Sys.echo(list2, "list2");
        Dynamic map = Dynamic.newMap(new Object[]{
                list.getAt(1), "aaa",
                "xyz", 12.3, "dt",
                new Date(), "bytes",
                new byte[] {1, 2, 3}});
        Sys.echo(map);
        Dynamic keys = map.keys();
        Sys.echo(keys);
        for (int i=0; i<keys.size(); i++) {
            Sys.echo(keys.getAt(i));
            Sys.echo(map.get(keys.getAt(i).asString()));
        }
        var mapBson = (BsonDocument)map.toBsonValue();
        Sys.echo(mapBson, "mapBson");
        var mapBytes = BsonData.EncodeToBytes(mapBson);
        Files.write(new File("C:/ProgramData/tmp.bson").toPath(), mapBytes);
        byte[] mapBytes2 = Files.readAllBytes(new File("C:/ProgramData/tmp.bson").toPath());
        var mapBson2 = BsonData.DecodeFromBytes(mapBytes2);
        Sys.echo(mapBson2, "mapBson2");
        var mapJson = Sys.toJson(map, true);
        Sys.echo(mapJson, "mapJson");
        var map2 = Sys.fromJson(mapJson);
        Sys.echo(map2, "map2");
        var j1 = "{\"dt\": {\"$date\": \"2024-01-19T05:53:14.6951234Z\"}}";
        var j1Obj = Sys.fromJson(j1);
        Sys.echo(j1Obj, "j1Obj");
        var j2 = "{\"dt\": {\"$date\": \"2024-01-19T05:53:14.6951234+09:00\"}}";
        var j2Obj = Sys.fromJson(j2);
        Sys.echo(j2Obj, "j2Obj");
    }

}
