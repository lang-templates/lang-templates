package demo;

import common.DirModel;
import system.Sys;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegexStringFilter {
    public static List<String> filterStringsByRegex(List<String> inputList, String regex) {
        if (!regex.startsWith("^")) regex = ".*" + regex;
        if (!regex.endsWith(("$"))) regex = regex + ".*";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return inputList.stream()
                .filter(str -> pattern.matcher(str).matches())
                .collect(Collectors.toList());
    }

    public static List<String> filterStringsBySubstring(List<String> inputList, String s) {
        return inputList.stream()
                .filter(str -> str.contains(s))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> inputList = new ArrayList<>(List.of(
                "apple", "banana", "cherry", "date", "elderberry",
                "fig", "grape", "honeydew", "kiwi", "lemon"
        ));

        String regex = "^[a-e].*$";  // a から e で始まる文字列にマッチ

        List<String> filteredList = filterStringsByRegex(inputList, regex);

        System.out.println("Original list: " + inputList);
        System.out.println("Filtered list: " + filteredList);

        var model = new DirModel("D:\\.repo\\base14");
        var list = model.getPathList();
        list = list.stream()
                .map(x -> x.replaceAll("\\\\", "/"))
                .toList();
//        list.stream()
//                .forEach((x) -> {
//                    Sys.println(x);
//                });
        List<String> list2 = filterStringsByRegex(list, "/JAVA01$");
        list2.stream()
                .forEach((x) -> {
                    Sys.println("[list2] " + x);
                });
        List<String> list3 = filterStringsByRegex(list, "/java[^/.]+$");
        list3.stream()
                .forEach((x) -> {
                    Sys.println("[list3] " + x);
                });
    }
}