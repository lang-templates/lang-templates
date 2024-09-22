package app;

import java.util.Arrays;
import java.util.List;

// import java.util.function.Consumer;

// https://atmarkit.itmedia.co.jp/ait/articles/1407/28/news023_3.html
public class MainStream2 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("A", "D", "B", "C", "E");

        // staticメソッドを参照する場合

        System.out.println("--- ラムダ式 ---");
        list.stream().forEach(value -> MainStream2.println(value));

        System.out.println("--- メソッド参照 ---");
        list.stream().forEach(MainStream2::println);
    }

    private static void println(String value) {
        System.out.println(value);
    }
}
