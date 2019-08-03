package chapter02.example03;

/**
 * 2.4尝试使用synchronized来停止一个线程
 */
public class ThreadStopping {

    public static void main(String[] args) {
        class StoppableThread extends Thread {
            private boolean stopped;

            @Override
            public void run() {
                synchronized (this) {
                    while (!stopped) {
                        System.out.println("running");
                    }
                }
            }

            synchronized void stopThread() {
                stopped = true;
            }
        }

        StoppableThread thd = new StoppableThread();
        thd.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        // 尝试获取对象锁，永远获取不到
        thd.stopThread();
    }
}
