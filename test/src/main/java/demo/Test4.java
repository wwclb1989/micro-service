package demo;

import java.util.Scanner;

public class Test4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("请输入你的三角形的三条边");
        double arr [] = new double [3];

        double s = 0;

        try {
            for (int i=0;i<=2;i++) {
                System.out.print("第" + (i + 1) + "边：");
                arr [i] = sc.nextDouble();
            }

            if (arr[0] <= 0 || arr[1] <= 0 || arr[2] <= 0) {
                throw new Exception("长度不能为负");
            }

            if (arr[0] + arr[1] <= arr[2] || arr[1] + arr[2] <= arr[1] || arr[1] + arr[2] <= arr[0]) {
                throw new Exception("三边长度不满足要求");
            }

            double p = (arr[0] + arr[1] + arr[2]) / 2;
            s = Math.sqrt ( p * (p - arr[0] ) * (p - arr[1]) * (p - arr[2] ) );
            System.out.println("area:" + s );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}
