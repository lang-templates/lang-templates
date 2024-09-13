package demo;

import system.Sys;

public class CallbackDemo {
    int total = 0;

    public interface I_Callback_of_Integer {
        int count();
        void handle(int i);
    }

    public static void main(String[] args) {
        new CallbackDemo().doit();
    }

    private void doit() {
        class MyCallback implements I_Callback_of_Integer {
            private int n = 0;

            public MyCallback(int n) {
                this.n = n;
            }

            @Override
            public int count() {
                return this.n;
            }

            @Override
            public void handle(int i) {
                Sys.echo(i);
                CallbackDemo.this.total += i;
            }
        }
        process(new MyCallback(3));
        Sys.echo(this.total, "this.total");
    }

    private static void process(I_Callback_of_Integer callback) {
        for (int i = 0; i < callback.count(); i++) {
            callback.handle(i + 1);
        }
    }
}
