package app;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
//import java.util.stream.LongStream;
import java.util.stream.Stream;

public class MainStream3 {

	public static void main(String[] args) {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5");
        list.stream()
        .map(BigDecimal::new)
        .forEach(System.out::println);
        
        list.stream()
        .map(BigDecimal::new)
        .forEach(x -> System.out.println(x.getClass().toString()));
        
    	Stream<String> stream = Stream.of("a", "b", "c", "d", "e");
    	//IntStream intStream = IntStream.of(1, 2, 3, 4, 5);
    	//DoubleStream doubleStream = DoubleStream.of(1.0, 2.0, 3.0, 4.0, 5.0);
    	//LongStream longStream = LongStream.of(1, 2, 3, 4, 5);

    	// Mapから変換
    	Map<Integer, String> map = new HashMap<>();
    	map.put(1, "A");
    	map.put(2, "B");
    	map.put(3, "C");
    	Stream<Map.Entry<Integer, String>> mapStream = map.entrySet().stream();

    	// 文字列から変換
    	String str = "ABCDE";
    	IntStream strStream = str.chars();

    	Stream.Builder<Integer> sb = Stream.builder();
    	sb.add(10);
    	sb.add(20);
    	Stream<Integer> buildStream =sb.build();
    	buildStream.forEach(System.out::println);
	}
}
