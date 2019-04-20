package demo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test11 {

    public static void main(String[] args) throws Exception {

        Class cls;  // 类类型，获取类类型有三种方式

        // 1.第一种方式，通过类名获得
        cls = Animal.class;
        // 2.第二种方式，通过对象获得
        cls = new Animal().getClass();
        // 3.第三种方式，类全名
        cls = Class.forName("demo.Animal");

        System.out.println("类名：" + cls.getSimpleName());
        System.out.println("类全名：" + cls.getName());

        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("成员名(包括私有成员）：" + field.getName());
        }

        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("方法名（包括私有方法）：" + method.getName());
        }

        // 通过反射创建一个对象
        Animal animal = (Animal) cls.newInstance();

        // 设置id与name的值
        Field id = cls.getDeclaredField("id");
        id.set(animal, 2);
        Field name = cls.getDeclaredField("name");
        name.setAccessible(true);   // 设置私有成员可以访问
        name.set(animal, "tom");

        // 通过反射调用方法
        Method showName = cls.getDeclaredMethod("showName");
        showName.invoke(animal);

        // 获取名字为speak，参数为String类型的方法
        Method speak = cls.getDeclaredMethod("speak", String.class);
        speak.setAccessible(true);  // 设置私有方法可以调用
        speak.invoke(animal, "hello! have a dream.");
    }

}
