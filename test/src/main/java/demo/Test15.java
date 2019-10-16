package demo;

import java.util.Arrays;
import java.util.Comparator;

public class Test15 {

    public static void main(String[] args) {
        Dog[] array = new Dog[3];

        array[0] = new Dog("java老师", 250, 3);
        array[1] = new Dog("数据库老师", 100, 5);
        array[2] = new Dog("前端老师", 150, 4);

        // 按体重升序
        Arrays.sort(array, Comparator.comparingInt(Dog::getWeight));
        for (Dog dog : array) {
            System.out.println(dog);
        }

        // 按年龄降序
        Arrays.sort(array, Comparator.comparingInt(Dog::getAge).reversed());
        for (Dog dog : array) {
            System.out.println(dog);
        }

    }

}

class Dog {
    private String name;

    private int weight;

    private int age;

    public Dog(String name, int weight, int age) {
        this.name = name;
        this.weight = weight;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", age=" + age +
                '}';
    }
}
