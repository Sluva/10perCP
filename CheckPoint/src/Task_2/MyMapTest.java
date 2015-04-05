package Task_2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyMapTest {
	MyMap<Integer, Character> myMap;

	@Before
	public void setUp() throws Exception {
		myMap = new MyMap<>(1000);
		myMap.put(0, 'a');
		myMap.put(10, 'b');
		myMap.put(20, 'c');
		myMap.put(30, 'd');
		myMap.put(40, 'e');
	}

	@After
	public void tearDown() throws Exception {
		myMap.clear();
		myMap = null;
	}

	@Test
	public void testClear() {
		myMap.clear();
		assertTrue(myMap.size() == 0);
	}

	@Test
	public void testContainsKey() {
		for (int i = 0; i < 5; i++) {
			assertTrue(myMap.containsKey(i * 10));
			assertFalse(myMap.containsKey(i + 1));
		}
	}

	@Test
	public void testContainsValue() {
		for (char i = 'a'; i < 'f'; i++) {
			assertTrue(myMap.containsValue(i));
			assertFalse(myMap.containsValue(i + 5));
		}
	}

	@Test
	public void testGet() {
		assertEquals('d', myMap.get(30).charValue());
	}

	@Test
	public void testSize() {
		assertEquals(5, myMap.size());
	}

	@Test
	public void testIsEmpty() {
		assertFalse(myMap.isEmpty());
		myMap.clear();
		assertTrue(myMap.isEmpty());
	}

	@Test
	public void testPut() {
		myMap.put(50, 'f');
		assertTrue(myMap.size() == 6);
		assertTrue(myMap.get(50) == 'f');
	}

	@Test
	public void testRemove() {
		myMap.remove(10);
		assertTrue(myMap.size() == 4);
		assertTrue(myMap.get(10) == null);
	}

	@Test
	public void testTime() throws InterruptedException {
		Thread.sleep(2000);
		assertTrue(myMap.size() == 0);
		myMap.put(10, 'b');
		Thread.sleep(400);
		assertTrue(myMap.size() == 1);
		myMap.put(20, 'c');
		Thread.sleep(400);
		assertTrue(myMap.size() == 2);
		myMap.put(30, 'd');
		Thread.sleep(400);
		assertTrue(myMap.size() == 2);
	}
}
