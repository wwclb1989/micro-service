package demo;

public class EmployeeDemo {

    public static void main(String[] args) {
        Employee employee = new BachelorEmployee();
        employee.id = 1;
        employee.name = "播妞";
        employee.salary = 1000000.0;
        System.out.println(employee);
        employee.raise(0.4);
        System.out.println(employee);
    }

}

class Employee {
    public String name;

    public int id;

    public double salary;

    public double raise(double percent) {
        salary *= 1 + percent;
        return salary;
    }
}

class BachelorEmployee extends Employee {

    @Override
    public double raise(double percent) {
        salary *= 1 + 2 * percent;
        return salary;
    }

    @Override
    public String toString() {
        return "BachelorEmployee{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", salary=" + salary +
                '}';
    }
}