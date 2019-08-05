package chapter04.example04;

/**
 * 将一个对象从父线程传到子线程中
 */
public class InheritableThreadLocalDemo {
    private static final InheritableThreadLocal<Integer> intVal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        Runnable rP = () -> {
          intVal.set(new Integer(10));
          Runnable rC = () -> {
              String name = Thread.currentThread().getName();
              System.out.printf("%s %d%n", name, intVal.get());
          };
          Thread thdChild = new Thread(rC);
          thdChild.setName("Child");
          thdChild.start();
        };

        new Thread(rP).start();
    }
}

class XXX extends InheritableThreadLocal<Integer> {
    @Override
    protected Integer childValue(Integer parentValue) {

        return parentValue / 2;
    }
}
