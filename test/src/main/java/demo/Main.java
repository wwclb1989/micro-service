package demo;

enum Week {
    Monday(1), ThirthDay(3), Friday(5), Sunday(7);
    private int value;
    Week(int p) {value = p;}
    int getValue() {return value;}
}

enum Car {
    lamborghini(900),tata(2),audi(50),fiat(15),honda(12);
    private int price;
    Car(int p) {
        price = p;
    }
    int getPrice() {
        return price;
    }
}
public class Main {
    public static void main(String args[]){


        for (Week week : Week.values()) {
            System.out.println(week + ":" + week.getValue());
        }


        System.out.println("所有汽车的价格：");

        System.out.println(Car.tata.getPrice());
        System.out.println(Car.tata.name());


        for (Car c : Car.values())
            System.out.println(c + " 需要 "
                    + c.getPrice() + " 千美元。");
    }
}

