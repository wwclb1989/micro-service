package demo;

import java.util.Scanner;

// 养成习惯，类名首字母大写
public class Test6 {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		// 得分数
		int score = 0;
		int rightNum = 0;
		
		// 10道题，10 次循环
		for (int i = 0; i < 10; i++) {
			
			// 生成a,b随机数，范围1~10
			int a = (int) (Math.random() * 10) + 1;
			int b = (int) (Math.random() * 10) + 1;
			
			String sep;	// 计算符
			int result;	// 正确答案
			
			// 随机生成 + - * / 四种运算符
			int c = (int) (Math.random() * 4);	// 这个范围是0~3
			switch(c) {
				// 加法
				case 0 :
					sep = "+";
					result = a + b;
					break;
				
				// 减法
				case 1 :
					sep = "-";
					result = a - b;
					break;
					
				// 乘法
				case 2 :
					sep = "*";
					result = a * b;
					break;
					
				// 除法，关于整数的除法计算，暂时不要管，两个整型计算除法的结果还是个整型
				default :
					sep = "/";
					result = a / b;
			}
	
			// 输出题目
			System.out.print(a + sep + b + "=");
			
			// 输入答案
			if (result == sc.nextInt()) {
				score += 10;	// 回答正确加10分
				rightNum++;		// 正确个数+1
			}
			
		}
		
		// 输出分数
		System.out.println("正确个数：" + rightNum);
		System.out.println("您的得分数是：" + score);
		
	}
}
