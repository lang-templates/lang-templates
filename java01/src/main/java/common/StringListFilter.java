package common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import system.Sys;

public class StringListFilter {
  public static List<String> filterByRegex(List<String> inputList, String regex) {
    Pattern pattern = Pattern.compile(RegexTool.toPartialRegex(regex), Pattern.CASE_INSENSITIVE);
    return inputList.stream()
        .filter(str -> pattern.matcher(str).matches())
        .collect(Collectors.toList());
  }

  public static List<String> filterBySubstring(List<String> inputList, String s) {
    return inputList.stream().filter(str -> str.contains(s)).collect(Collectors.toList());
  }

  public static void main(String[] args) {
    // demo program
    List<String> inputList =
        new ArrayList<>(
            List.of(
                "apple",
                "banana",
                "cherry",
                "date",
                "elderberry",
                "fig",
                "grape",
                "honeydew",
                "kiwi",
                "lemon"));

    String regex = "^[a-e].*$"; // a から e で始まる文字列にマッチ

    List<String> filteredList = filterByRegex(inputList, regex);

    System.out.println("Original list: " + inputList);
    System.out.println("Filtered list: " + filteredList);

    var model = new DirModel("D:\\.repo\\base14");
    var list = model.getPathList(true);
    list = list.stream().map(x -> x.replaceAll("\\\\", "/")).collect(Collectors.toList());
    //        list.stream()
    //                .forEach((x) -> {
    //                    Sys.println(x);
    //                });
    List<String> list2 = filterByRegex(list, "/JAVA01$");
    list2.stream()
        .forEach(
            (x) -> {
              Sys.println("[list2] " + x);
            });
    List<String> list3 = filterByRegex(list, "/java[^/.]+$");
    list3.stream()
        .forEach(
            (x) -> {
              Sys.println("[list3] " + x);
            });
  }
}
