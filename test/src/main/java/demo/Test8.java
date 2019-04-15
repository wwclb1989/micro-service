package demo;

import java.util.Scanner;

public class Test8 {
    public static void main(String[] args) {

        System.out.println("请输入8~10位数的数");

        Scanner sc = new Scanner(System.in);

        int x = sc.nextInt();

        //System.out.println((x+"").length()-5);

        if ((x+"").length() <= 5) { //判断语句，如果输入小于5位数，那么报错

            System.out.println("您的输入范围有误，请输入大于5位数的数");
            return;     // 方法结束
        }

        int result = ((x+"").length()-5); //比5位数还大的数-5位数的位数。
        //System.out.println(result);

        for (int i = 0; i < result; i++) {
            x /= 10;
        }
        System.out.println(x);

//        int surplus=1;
//
//        for (int i=1;i<=result;i++) {	//除于多余位数得出5位数的循环
//
//            surplus*=10;	//如果多余1位,那么就是/10,如果多余2位，那么/100,如果多余n位，那么/10的n次方.
//        }
//        if ((x+"").length()>5) {
//
//            System.out.println((x+"".length())/(surplus));
//        }
    }
}
