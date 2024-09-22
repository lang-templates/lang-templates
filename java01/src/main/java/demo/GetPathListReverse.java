package demo;

import common.DirModel;
import java.util.Collections;
import java.util.Comparator;

public class GetPathListReverse {
    public static void main(String[] args) {
        var dirModel = new DirModel("C:\\Program Files\\Zulu\\zulu-11");
        var list =
                dirModel.getPathList(
                        true,
                        (path, level) -> {
                            if (path.contains("/demo/")) return false;
                            if (path.contains("/legal/")) return false;
                            return true;
                        });
        Collections.shuffle(list);
        Collections.sort(list, Comparator.reverseOrder());
        list.stream().forEach(System.out::println);
    }
}
