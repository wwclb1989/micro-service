package chapter04.example06;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 以大约1秒的间隔不断显示当前系统时间
 */
public class TimerDemo {
    public static void main(String[] args) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis());
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);
    }
}
