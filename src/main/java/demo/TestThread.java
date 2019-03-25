package demo;

public class TestThread {

	public static void main(String[] args) {
		new Thread(new ThreadClass("线程1")).start();
		new Thread(new ThreadClass("线程2")).start();
		
	}
	
	
}
