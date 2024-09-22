package demo;

import common.DirModel;

public class GetPathListReverse {
    public static void main(String[] args) {
        var dirModel = new DirModel("C:\\Program Files\\Zulu\\zulu-11");
        var list = dirModel.getPathListReverse(true);
        list.stream()
                .forEach(System.out::println);
    }
}
