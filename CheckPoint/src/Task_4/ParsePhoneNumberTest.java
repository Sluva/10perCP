package Task_4;

import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParsePhoneNumberTest {
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
		args[0] = "./src/Task_4/files/phone.pos";
		ParsePhoneNumber.main(args);
		assertTrue(ParsePhoneNumber.phoneBook.size() == 3);
		assertEquals(ParsePhoneNumber.phoneBook.get(0), "1111154");
		assertEquals(ParsePhoneNumber.phoneBook.get(1), "89136488264");
		assertEquals(ParsePhoneNumber.phoneBook.get(2), "+11234123456");
		ParsePhoneNumber.phoneBook.clear();
		args[0] = "./src/Task_4/files/phone2.pos";
		fileWriter = new FileWriter(args[0]);
		fileWriter.write("111 11 54\n8 (913) 648 82 64\n+1 1234 12-2334-56");
		fileWriter.close();
		ParsePhoneNumber.main(args);
		assertTrue(ParsePhoneNumber.phoneBook.size() == 2);
		assertEquals(ParsePhoneNumber.phoneBook.get(0), "1111154");
		assertEquals(ParsePhoneNumber.phoneBook.get(1), "89136488264");
		ParsePhoneNumber.phoneBook.clear();
		args[0] = "./src/Task_4/files/phone2.pos";
		fileWriter = new FileWriter(args[0]);
		fileWriter.write("1111154\n8 [913] 648 82 64\n+1 1234 12-34-56");
		fileWriter.close();
		ParsePhoneNumber.main(args);
		assertTrue(ParsePhoneNumber.phoneBook.size() == 1);
		assertEquals(ParsePhoneNumber.phoneBook.get(0), "+11234123456");
		ParsePhoneNumber.phoneBook.clear();
	}
}
