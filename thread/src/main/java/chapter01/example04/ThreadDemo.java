package chapter01.example04;

/**
 * 1.2.3线程睡眠
 */
public class ThreadDemo {

    public static void main(String[] args) {
        Runnable r = () -> {
            String name = Thread.currentThread().getName();
            int count = 0;
            while (!Thread.interrupted()) {
                System.out.println(name + ":" + count++);
            }
        };

        Thread thdA = new Thread(r);
        Thread thdB = new Thread(r);
        thdA.start();
        thdB.start();

        try {
            // 主线程睡眠2秒
            Thread.sleep(2000);
        } catch (InterruptedException ie) {

        }

        thdA.interrupt();
        thdB.interrupt();
    }
}
