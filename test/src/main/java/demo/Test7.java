package demo;

public class Test7 {

    public static void main(String[] args) {

        for (int i = 30; i < 100; i++) {
            int cardNo = i * i;
            int a = cardNo % 10;  // 个位
            cardNo = cardNo / 10;
            int b = cardNo % 10;  // 十位
            cardNo = cardNo / 10;
            int c = cardNo % 10;  // 百位
            cardNo = cardNo / 10;
            int d = cardNo % 10;  // 千位

            if (a == b && c == d && a != c) {
                System.out.println(i * i);
            }

        }

    }

}
