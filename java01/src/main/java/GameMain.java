/*public*/ class Matango {
    public int hp;
    public int LEVEL = 10;
    char suffix;
    public void run() {
        System.out.println("お化けキノコ" + this.suffix + "は逃げ出した！");
    }
}

public class GameMain {
    public static void main(String[] args) {
        Matango a = new Matango();
        a.suffix = 'A';
        a.run();
        Matango b = new Matango();
        b.suffix = 'B';
        b.run();
    }
}

/*
 * 【実行結果】
 * お化けキノコAは逃げ出した！
 * お化けキノコBは逃げ出した！
 */
