package chapter04.example05;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 单次执行任务的示例
 */
public class TimerDemo {
    public static void main(String[] args) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.printf("alarm going off");
                System.exit(0);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }
}
