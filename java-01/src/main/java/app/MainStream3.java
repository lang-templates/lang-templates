package app;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class MainStream3 {

	public static void main(String[] args) {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5");
        list.stream()
        .map(BigDecimal::new)
        .forEach(System.out::println);
	}

}
