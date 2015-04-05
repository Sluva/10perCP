package Task_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.*;

public class SumOfSeries_Method_2 {

	private static Scanner sc;
	static volatile long sum = 0;

	public static class CallableSample implements Callable<Integer> {
		final int i;

		public CallableSample(int i) {
			this.i = i;
		}

		public Integer call() throws Exception {
			return (int) Math.pow(2, (i - (i % 2 == 0 ? 1 : -1)));
		}
	}

	public static void main(String[] args) throws FileNotFoundException,
			InterruptedException, ExecutionException {
		if (args.length == 0)
			sc = new Scanner(System.in);
		else {
			File file = new File(args[0]);
			sc = new Scanner(file);
		}
		do {
			sum = 0;
			int N = sc.nextInt();
			int threads = sc.nextInt();
			@SuppressWarnings("unchecked")
			Future<Integer>[] tasks = new FutureTask[N];
			ExecutorService es = Executors.newFixedThreadPool(threads);
			for (int i = 1; i <= N; i++) {
				tasks[i - 1] = es.submit(new CallableSample(i));
			}
			for (int i = 1; i <= N; i++) {
				while (!tasks[i - 1].isDone())
					;
				sum += tasks[i - 1].get();
			}
			System.out.println(sum);
			es.shutdown();
		}while (args.length != 0 && sc.hasNext());
	}
}
