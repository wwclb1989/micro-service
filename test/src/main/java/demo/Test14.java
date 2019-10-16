package demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test14 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        list.add("你的");
        list.add("老师");
        list.add("是");
        list.add("一个");
        list.add("傻逼");

        // 第一种遍历
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (str.equals("你的")) {
                iterator.remove();
            }
            System.out.println(str);
        }
        System.out.println("第一次遍历后list:" + list);

        // 第二种遍历
        for (String str : list) {
            System.out.println(str);
            if (str.equals("老师")) {
                // 会抛出异常java.util.ConcurrentModificationException
//                list.remove(str);
            }
        }

        // 第三种遍历
        list.forEach(s -> {
            System.out.println(s);
            if (s.equals("老师")) {
                // 会抛出异常java.util.ConcurrentModificationException
//                list.remove(s);
            }
        });


    }


}
