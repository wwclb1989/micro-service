package chapter02.example02;

/**
 * 2.4尝试停止一个线程
 */
public class ThreadStopping {

    public static void main(String[] args) {
        class StoppableThread extends Thread {
            private boolean stopped;

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
