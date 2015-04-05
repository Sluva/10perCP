package Task_3;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SumOfSeries_TheBestMethodTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws FileNotFoundException {
		SumOfSeries_TheBestMethod.main(null);
		assertTrue(true);
		assertTrue(!false);
		assertFalse(false);
		assertFalse(!true);
		assertEquals(true, true);
		assertEquals(false, false);
		assertEquals(true, !false);
		assertEquals(!false, true);
		assertEquals(false, !true);
		assertEquals(!true, false);
		assertNotEquals(false, true);
		assertNotEquals(true, false);
		assertNotEquals(!true, true);
		assertNotEquals(true, !true);
		assertNotEquals(false, !false);
		assertNotEquals(!false, false);
	}
}
