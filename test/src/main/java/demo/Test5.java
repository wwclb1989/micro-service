package demo;

public class Test5 {

    public static void main(String[] args) {
//        Person person1 = new Person();
//        Person person2 = new Person();
        Person person1 = Person.getPersonInstance();
        Person person2 = Person.getPersonInstance();
        System.out.println(person1);
        System.out.println(person2);
    }
}

// 线程不安全
//class Person {
//
//    private static Person person;
//
//    private Person() {}
//
//    public static Person getPersonInstance() {
//        if (person == null) {
//            person = new Person();
//        }
//        return person;
//    }
//
//}

// 线程安全
class Person {

    private static Person person = new Person();

    private Person() {}

    public static Person getPersonInstance() {
        return person;
    }

}