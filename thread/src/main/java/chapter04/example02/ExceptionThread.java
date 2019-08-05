package chapter04.example02;

/**
 * 未捕获异常处理器示例
 */
public class ExceptionThread {
    public static void main(String[] args) {
        Runnable r = () -> {int x = 1 / 0;};

        Thread thd = new Thread(r);

        Thread.UncaughtExceptionHandler uceh = (t, e) -> System.out.println("Caught throwable " + e + " for thread " + t);

        thd.setUncaughtExceptionHandler(uceh);

        uceh  = (t, e) -> {
            System.out.println("Default uncaught exception handler");
            System.out.println("Caught throwable " + e + " for thread " + t);
        };

        Thread.setDefaultUncaughtExceptionHandler(uceh);
        thd.start();
    }
}
