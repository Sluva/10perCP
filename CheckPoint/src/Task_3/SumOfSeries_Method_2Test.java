package Task_3;

import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SumOfSeries_Method_2Test {
	FileWriter fileWriter;
	String[] args;

	@Before
	public void setUp() throws Exception {
		args = new String[1];
	}

	@After
	public void tearDown() throws Exception {
		args = null;
		fileWriter = null;
	}

	@Test
	public void test() throws InterruptedException, IOException,
			ExecutionException {
		args[0] = "./src/Task_3/files/test1.txt";
		fileWriter = new FileWriter(args[0]);
		fileWriter.write(10 + " " + 5);
		fileWriter.close();
		SumOfSeries_Method_2.main(args);
		assertEquals(2046, SumOfSeries_Method_2.sum);
		args[0] = "./src/Task_3/files/test2.txt";
		fileWriter = new FileWriter(args[0]);
		fileWriter.write(5 + " " + 5);
		fileWriter.close();
		SumOfSeries_Method_2.main(args);
		assertEquals(94, SumOfSeries_Method_2.sum);
		args[0] = "./src/Task_3/files/test3.txt";
		fileWriter = new FileWriter(args[0]);
		fileWriter.write(15 + " " + 5);
		fileWriter.close();
		SumOfSeries_Method_2.main(args);
		assertEquals(98302, SumOfSeries_Method_2.sum);
		args[0] = "./src/Task_3/files/inputt.txt";
		fileWriter = new FileWriter(args[0]);
		fileWriter.write(10 + " " + 2 + "\n");
		fileWriter.write(1 + " " + 8 + "\n");
		fileWriter.write(20 + " " + 1);
		fileWriter.close();
		SumOfSeries_Method_2.main(args);
		assertEquals(2097150, SumOfSeries_Method_2.sum);
	}
}
