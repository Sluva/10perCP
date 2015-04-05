package Task_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * ���������� � ������ ������ (����� ��� N = 60) �������� ���� ��������� ������, ��� ��������. 
 * ����� �� � ������� ������� ������ (������ ������� ���������) ���� ���� ���� (������� �� ������ �������)
 * ��� ��������� � ���� while ������ "Thread.currentThread().sleep(10);" ��������� ��������� �� ������
 */
public class SumOfSeries_Method_1 extends Thread {
	static int i = 1;
	static volatile long sum = 0;
	static int N;
	private static Scanner sc;

	@Override
	public void run() {
		int t = i++;
		long localSum = 0;
		while (t <= N) {
			localSum += Math.pow(2, (t - Math.pow(-1, t)));
			t = i++;
		}
		sum += localSum;
	}

	public static void main(String[] args) throws InterruptedException,
			FileNotFoundException {
		if (args.length == 0)
			sc = new Scanner(System.in);
		else {
			File file = new File(args[0]);
			sc = new Scanner(file);
		}
		do {
			sum = 0;
			i = 0;
			N = sc.nextInt();
			int threads = sc.nextInt();
			SumOfSeries_Method_1[] pool = new SumOfSeries_Method_1[threads];
			for (int j = 0; j < threads; j++) {
				pool[j] = new SumOfSeries_Method_1();
				pool[j].start();
			}
			for (int j = 0; j < threads; j++) {
				pool[j].join();
			}
			System.out.println(sum);
		} while (args.length != 0 && sc.hasNext());
	}
}
