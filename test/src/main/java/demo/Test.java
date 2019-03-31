package demo;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		int number = scanner.nextInt();

		// 用来统计数量吧~
		int count = 0;

		// 比如15 = 7 + 8 最大的开始数字是7 = 15/2
		for (int i = 1; i <= number / 2; i++) {
			int sum = 0;
			// 开始累加
			for (int j = i; j <= number / 2 + 1; j++) {
				sum += j;

				// 如果sum已经大于number，说明以i开头连续数字相加得不到number
				if (sum > number) {
					break;
				}

				// 如果sum = number
				if (sum == number) {
					System.out.print(number + "=");
					for (int k = i; k <= j; k++) {
						if (k != j) {
							System.out.print(k + "+");
						} else {
							System.out.println(k);
						}

					}
					count++;
				}

			}
		}

		if (count == 0) {
			System.out.println(number + " = none");
		}

	}
	
	
	public static void print() {
		if (true) {
			
			System.out.println("每晚夜里自我独行，随处荡，多冰冷");
			return;
		}
		
		System.out.println("以往为了自我挣扎，从不知，她的痛苦");
	}
}
