package demo;

public class ThreadClass implements Runnable {

	private String name;
	
	public ThreadClass(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		
		try {
			for (int i = 0; i < 10; i++) {
				System.out.println(name);
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
