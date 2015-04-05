package Task_5;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CodeCleanerTest {
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
	public void test() throws IOException {
		args[0] = "./src/Task_5/files/CleanerTest.java";
		fileWriter = new FileWriter(args[0]);
		fileWriter.write("qwerty//qwerty\n");
		fileWriter.write("/*qwerty*/qwerty/*dsf\n");
		fileWriter.write("qwerty*/qwerty\"qwe//rty\"");
		fileWriter.close();
		CodeCleaner.main(args);
		File file = new File("./src/Task_5/files/CleanerTestDone.java");
		BufferedReader bf = new BufferedReader(new FileReader(file));
		assertEquals("qwerty", bf.readLine());
		assertEquals("qwerty", bf.readLine());
		assertEquals("qwerty\"qwe//rty\"", bf.readLine());
		bf.close();
	}
}
