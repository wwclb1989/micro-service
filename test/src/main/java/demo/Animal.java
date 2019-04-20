package demo;

public class Animal {

    public int id;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    private void speak(String message) {
        System.out.println(message);
    }

    public void showName() {
        System.out.println("id is " + id);
        System.out.println("name is " + name);
    }

}
