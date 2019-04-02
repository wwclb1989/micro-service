package demo;

import java.util.Scanner;

public class Test2 {
        /*
    键盘输入n*m的矩阵，输出其鞍点
    （即在矩阵中行的最大数，且列的最小数），如无鞍点，则输出"NO"。
    如输入
    87 90 110 98
    70 97 210 65
    98 45 120 30
    则输出110
     */
//    public static void main(String[] args) {
//
//        Scanner sc = new Scanner(System.in);
//
//        // x行y列
//        int x = sc.nextInt();
//        int y = sc.nextInt();
//        int[][] arr = new int[x][y];
//
//        // 输入数据
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = 0; j < arr[0].length; j++) {
//                arr[i][j] = sc.nextInt();
//            }
//        }
//
//        // 思路：每行的最大值，判断一下是否是当前列的最小值，如果是，说明是鞍点，输入该值
//
//        for (int i = 0; i < arr.length; i++) {
//            int max = arr[i][0];    // 将该列的第一个值赋值给max
//            int colNum = 0;         // 用来记录该行最大值所在的列
//
//            // 开始遍历第i行
//            for (int j = 0; j < arr[0].length; j++) {   // 这里要注意是arr[0].length或者a[i].length，是第二维的size，因为它不是方阵，m不等于n
//                if (max < arr[i][j]) {
//                    max = arr[i][j];
//                    colNum = j;
//                }
//            }
//
//            // 到这里该行最大值为max，且在第i行，第colNum列
//            // 再遍历第colNum列，看一下max是否是当前列的最小值
//            int min = arr[0][colNum];    // 记录当前列的最小值
//            for (int k = 0; k < arr.length; k++) {
//                if (arr[k][colNum] < min) {
//                    min = arr[k][colNum];
//                }
//            }
//
//            // 比较一下当前行的最大值与最大值所在列的最小值，看看是否相等，相等就是鞍点
//            if (max == min) {
//                System.out.println(max);
//            } else {
//                System.out.println("NO");
//            }
//
//        }
//
//    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // m行n列
        int m = sc.nextInt();
        int n = sc.nextInt();
        int[][] arr = new int[m][n];

        // 输入数据
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = sc.nextInt();
            }
        }

        // 遍历每一个数据，判断它是当前行最大的，并且是当前行最小的即可
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                // 当前值是a[i][j]，在第i行，第j列
                boolean flag = true;

                // 看一下是否是当前行的最大值
                for (int x = 0; x < arr[i].length; x++) {
                    if (arr[i][x] > arr[i][j]) {
                        flag = false;
                        break;
                    }
                }

                // 看一行是否是当前列的最小值
                for (int y = 0; y < arr.length; y++) {
                    if (arr[y][j] < arr[i][j]) {
                        flag = false;
                        break;
                    }
                }

                if (flag == true) {
                    System.out.println(arr[i][j]);
                }
            }
        }

    }

    // 返回一个维数据的最大值所在的列
//    public static int max(int [] arr) {
//
//        for (int i = 0; i < arr.length; i++) {
//
//        }
//    }

}
