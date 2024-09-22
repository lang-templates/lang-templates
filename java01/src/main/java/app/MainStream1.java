package app;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MainStream1 {

  public static void main(String[] args) {
    MainStream1 sample = new MainStream1();
    sample.process();
  }

  private void process() {
    List<String> list = Arrays.asList("A", "D", "B", "C", "E");

    // インスタンスのメソッドを参照する場合
    StringComparator comparator = new StringComparator();
    System.out.println("--- ラムダ式 ---");
    list.stream()
        .sorted((value1, value2) -> comparator.compare(value1, value2))
        .forEach((value) -> System.out.println(value));

    System.out.println("--- メソッド参照 ローカル変数---");
    list.stream().sorted(comparator::compare).forEach(System.out::println);

    System.out.println("--- メソッド参照 this---");
    list.stream().sorted(this::compareValues).forEach(System.out::println);
  }

  private class StringComparator implements Comparator<String> {
    public int compare(String value1, String value2) {
      return compareValues(value1, value2);
    }
  }

  public int compareValues(String value1, String value2) {
    return value1.compareTo(value2);
  }
}
