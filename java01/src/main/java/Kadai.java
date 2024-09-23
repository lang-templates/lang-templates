import java.util.Scanner;

public class Kadai {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            // ユーザー名の読み込み
            System.out.print("ユーザー名:");
            String user = scan.next();
            // パスワードの読み込み
            System.out.print("パスワード:");
            String pass = scan.next();
            if (user.equals("user") && pass.equals("password")) {
                System.out.println("認証されました。");
                break;
            }
            System.out.println("ユーザー名またはパスワードが正しくありません。再入力してください。");
        }
    }
}
