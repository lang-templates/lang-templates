package 練習用;

// 学生クラス
class Student{
    // フィールド
    private String number;
    private String name;
    private int birth;
    private double height;

    //コンストラクタ(1)
    public Student(String id, String n,int b,double h)
    {
        number = id;
        name = n;
        birth = b;
        height = h;
        System.out.println("学生番号が"+number+"で、名前が" + name + "、生年月日が"+(birth < 0 ? "不明" : birth)+ "、身長が"+(height < 0 ? "不明" : height + "cm")+"の生徒がいます。");
    }

    //コンストラクタ(2)
    public Student(String id) {
        this(id, "不明", -1, -1.0);
    }

    //コンストラクタ(3)
    public Student(String id, String name) {
        this(id, name, -1, -1.0);
    }

    //コンストラクタ(4)
    public Student(String id, String name, int birth) {
        this(id, name, birth, -1.0);
    }

    public void show()
    {
        System.out.println("学籍番号は"+number+"です。");
        System.out.println("名前は"+name+"です。");
        System.out.println("生年月日は"+(birth < 0 ? "不明" : birth)+"です。");
        System.out.println("身長は"+(height < 0 ? "不明" : height + "cm")+"です。");
    }
}

public class 課題17 {

    public static void main(String[] args)
    {
        Student student1 = new Student("0123","加藤", 20010410, 165);
        student1.show();

        Student student2 = new Student("0222","横井", 20010715);
        student2.show();

        Student student3 = new Student("4567", "田中");
        student3.show();


        Student student4 = new Student("8910");
        student4.show();
    }

}

/* 【実行結果】
 * 学生番号が0123で、名前が加藤、生年月日が20010410、身長が165.0cmの生徒がいます。
 * 学籍番号は0123です。
 * 名前は加藤です。
 * 生年月日は20010410です。
 * 身長は165.0cmです。
 * 学生番号が0222で、名前が横井、生年月日が20010715、身長が不明の生徒がいます。
 * 学籍番号は0222です。
 * 名前は横井です。
 * 生年月日は20010715です。
 * 身長は不明です。
 * 学生番号が4567で、名前が田中、生年月日が不明、身長が不明の生徒がいます。
 * 学籍番号は4567です。
 * 名前は田中です。
 * 生年月日は不明です。
 * 身長は不明です。
 * 学生番号が8910で、名前が不明、生年月日が不明、身長が不明の生徒がいます。
 * 学籍番号は8910です。
 * 名前は不明です。
 * 生年月日は不明です。
 * 身長は不明です。
 */