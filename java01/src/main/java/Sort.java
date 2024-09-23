import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Sort {
    public static void main(String[] args) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int[] number = new int[5];
            for (int i = 0; i < number.length; i++) {
                try {
                    System.out.print("" + (i + 1) + "番目の整数: ");
                    number[i] = Integer.parseInt(reader.readLine());
                } catch (Exception ignored) {
                    System.out.println("" + (i + 1) + "番目の入力に誤りがあります");
                    i--;
                }
            }
            Arrays.sort(number);
            for (int Max : number) {
                System.out.println(Max);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
