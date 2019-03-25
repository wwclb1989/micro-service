package demo;

import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		
		Map map = new HashMap();
		System.out.println(map.get(null));
		map.put(null, "123");
		System.out.println(map.get(null));
//		print();
	}
	
	
	public static void print() {
		if (true) {
			
			System.out.println("每晚夜里自我独行，随处荡，多冰冷");
			return;
		}
		
		System.out.println("以往为了自我挣扎，从不知，她的痛苦");
	}
}
