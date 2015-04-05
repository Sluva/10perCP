package Task_2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyLinkedListTest {
	MyLinkedList<Integer> myLinList;

	@Before
	public void setUp() throws Exception {
		myLinList = new MyLinkedList<>(1000);
		myLinList.add(0);
		myLinList.add(10);
		myLinList.add(20);
		myLinList.add(30);
		myLinList.add(40);
	}

	@After
	public void tearDown() throws Exception {
		myLinList.clear();
		myLinList = null;
	}

	@Test
	public void testGet() {
		for (int i = 0; i < myLinList.size(); i++) {
			assertEquals(i * 10, myLinList.get(i).intValue());
		}
	}

	@Test
	public void testAdd() {
		myLinList.add(50);
		myLinList.add(60);
		for (int i = 0; i < myLinList.size(); i++) {
			assertEquals(i * 10, myLinList.get(i).intValue());
		}
	}

	@Test
	public void testAddIndex() {
		myLinList.add(1, 5);
		myLinList.add(3, 15);
		myLinList.add(5, 25);
		myLinList.add(7, 35);
		for (int i = 0; i < myLinList.size(); i++) {
			assertEquals(i * 5, myLinList.get(i).intValue());
		}
	}

	@Test
	public void testSize() {
		assertEquals(5, myLinList.size());
	}

	@Test
	public void testClear() {
		myLinList.clear();
		assertEquals(0, myLinList.size());
	}

	@Test
	public void testContains() {
		for (int i = 0; i < myLinList.size(); i++) {
			assertTrue(myLinList.contains(10 * i));
			assertFalse(myLinList.contains(-i - 1));
		}
	}

	@Test
	public void testIndexOf() {
		myLinList.add(10);
		myLinList.add(20);
		for (int i = 0; i < 5; i++) {
			assertEquals(i, myLinList.indexOf(10 * i));
		}
	}

	@Test
	public void testLastIndexOf() {
		myLinList.add(10);
		myLinList.add(20);
		assertEquals(0, myLinList.lastIndexOf(0));
		assertEquals(5, myLinList.lastIndexOf(10));
		assertEquals(6, myLinList.lastIndexOf(20));
		assertEquals(3, myLinList.lastIndexOf(30));
		assertEquals(4, myLinList.lastIndexOf(40));
	}

	@Test
	public void testIsEmpty() {
		assertFalse(myLinList.isEmpty());
		myLinList.clear();
		assertTrue(myLinList.isEmpty());
	}

	@Test
	public void testRemoveIndex() {
		myLinList.remove(3);
		assertTrue(myLinList.size() == 4);
		for (int i = 0; i < 3; i++) {
			assertEquals(10 * i, myLinList.get(i).intValue());
		}
		assertEquals(40, myLinList.get(3).intValue());
	}

	@Test
	public void testRemoveObject() {
		myLinList.remove(new Integer(30));
		assertTrue(myLinList.size() == 4);
		for (int i = 0; i < 3; i++) {
			assertEquals(10 * i, myLinList.get(i).intValue());
		}
		assertEquals(40, myLinList.get(3).intValue());
	}

	@Test
	public void testContainsAll() {
		assertTrue(myLinList.containsAll(myLinList));
		MyLinkedList<Integer> buff = new MyLinkedList<Integer>(1000);
		buff.add(-10);
		assertFalse(myLinList.containsAll(buff));
	}

	@Test
	public void testAddCollIndex() {
		int j = 2;
		myLinList.addAll(j, myLinList);
		for (int i = 1; i < j; i++) {
			assertEquals(i * 10, myLinList.get(i).intValue());
		}
		for (int i = j; i < j + 5; i++) {
			assertEquals((i - j) * 10, myLinList.get(i).intValue());
		}
		for (int i = j + 5; i < myLinList.size(); i++) {
			assertEquals((i - 5) * 10, myLinList.get(i).intValue());
		}
	}

	@Test
	public void testSet() {
		for (int i = 0; i < myLinList.size(); i++) {
			myLinList.set(i, -i * 10);
		}
		for (int i = 0; i < myLinList.size(); i++) {
			assertEquals(myLinList.get(i).intValue(), -i * 10);
		}
	}

	@Test
	public void testToArray() {
		Object[] buff = myLinList.toArray();
		for (int i = 0; i < buff.length; i++) {
			assertEquals(buff[i], i * 10);
		}
	}

	@Test
	public void testTime() throws InterruptedException {
		Thread.sleep(1000);
		assertTrue(myLinList.size() == 0);
		myLinList.add(10);
		Thread.sleep(400);
		assertTrue(myLinList.size() == 1);
		myLinList.add(20);
		Thread.sleep(400);
		assertTrue(myLinList.size() == 2);
		myLinList.add(30);
		Thread.sleep(400);
		assertTrue(myLinList.size() == 2);
	}
}
