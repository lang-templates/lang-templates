package demo;

import common.DirModel;

import java.util.Collections;

public class GetPathListReverse {
    public static void main(String[] args) {
        var dirModel = new DirModel("C:\\Program Files\\Zulu\\zulu-11");
        var list = dirModel.getPathList(true, path -> {
            if (path.contains("/demo/")) return false;
            if (path.contains("/legal/")) return false;
            return true;
        });
        Collections.shuffle(list);
        list.stream()
                .forEach(System.out::println);
    }
}
