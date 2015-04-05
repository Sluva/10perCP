package Task_3;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class SumOfSeries_TheBestMethod {
	public static boolean inputConsole = true;

	public static void main(String[] args) throws FileNotFoundException {
		@SuppressWarnings("resource")
		int N = (new Scanner(System.in)).nextInt();
		long sum = (((N & 1) == 0 ? 2 : 3) << N) - 2;
		System.out.println(sum);
	}
}
