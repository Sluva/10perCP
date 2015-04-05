package Task_6;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FrequencyOfWordsTest {
	public FrequencyOfWords FoW;

	@Before
	public void setUp() throws Exception {
		FoW = new FrequencyOfWords();
	}

	@After
	public void tearDown() throws Exception {
		FoW = null;
	}

	@SuppressWarnings("static-access")
	@Test
	public void test() {
		FoW.main(null);
		assertEquals(415, FoW.mainMap.get("stay").intValue());
		assertEquals(1732, FoW.mainMap.get("go").intValue());
		assertEquals(100, FoW.mainMap.get("went").intValue());
		assertEquals(347, FoW.mainMap.get("boy").intValue());
		assertEquals(53, FoW.mainMap.get("girl").intValue());
	}
}
