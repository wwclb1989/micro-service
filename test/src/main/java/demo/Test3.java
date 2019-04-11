package demo;

import java.util.Scanner;

public class Test3 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("please input the number:");
        long n = scanner.nextLong();

        try {
            // f(n)方法可能抛出异常
            System.out.println("result:" + f(n));

            System.out.println("发生异常之后，try块后面的代码都不会执行，比如这句代码就不会执行");
        } catch (Exception e) {
            // 在这里捕获异常，发生了异常时要做的事
            System.out.println("发生了异常");
            System.out.println("查看异常中的信息：" + e.getMessage());
        } finally {
            System.out.println("收尾工作");
        }


    }

    public static long f(long n) {

        if (n < 1) {
            throw new RuntimeException("参数输入错误！");
        }

        if (n == 1) {
            return 0;
        } else if (n == 2) {
            return 1;
        } else {
            return (n - 1) * (f(n - 1) + f(n - 2));
        }

    }
}
