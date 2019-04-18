package demo;

import java.util.ArrayList;
import java.util.List;

public class Test10 {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("so");
        list.add("beautiful");
        list.add("everyday");

        // 循环方式1
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // 循环方式2
        for (String str : list) {
            System.out.println(str);
        }

        int[] arr = new int[]{1, 2, 3, 4, 5};

        for (int i : arr) {
            System.out.println(i);
            System.err.println(i);
        }


    }

}
