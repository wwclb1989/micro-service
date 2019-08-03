package chapter02.example04;

/**
 * 尝试通过volatile关键字停止一个线程
 */
public class ThreadStopping {

    public static void main(String[] args) {
        class StoppableThread extends Thread {
            private volatile boolean stopped;

            @Override
            public void run() {
                while (!stopped) {
                    System.out.println("running");
                }
            }

            void stopThread() {
                stopped = true;
            }
        }

        StoppableThread thd = new StoppableThread();
        thd.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        thd.stopThread();
    }
}
