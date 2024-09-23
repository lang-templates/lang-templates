import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MyThread extends Thread {
    private int count;

    public MyThread(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.count; i++) {
            ThreadDemo.lock.lock();
            try {
                int total = ThreadDemo.total;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    ;
                }
                total += 1;
                ThreadDemo.total = total;
            } finally {
                ThreadDemo.lock.unlock();
            }
        }
    }
}

public class ThreadDemo {
    public static final Lock lock = new ReentrantLock(true);
    public static int total = 0;

    public static void main(String[] args) throws Exception {
        MyThread t1 = new MyThread(25);
        MyThread t2 = new MyThread(25);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(ThreadDemo.total);
    }
}
