public class Main {
    public static void main(String[] args) {
        System.out.println(processInt(10));
    }

    public static int processInt(int num) {
        switch (num) {
            case 10:
                return num * 10;
        }
        return -1;
    }
}
