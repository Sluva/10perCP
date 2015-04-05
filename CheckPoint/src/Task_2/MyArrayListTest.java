package Task_2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyArrayListTest {
	MyArrayList<Integer> myArrList;

	@Before
	public void setUp() throws Exception {
		myArrList = new MyArrayList<>(20, 1000);
		myArrList.add(0);
		myArrList.add(10);
		myArrList.add(20);
		myArrList.add(30);
		myArrList.add(40);
	}

	@After
	public void tearDown() throws Exception {
		myArrList.clear();
		myArrList = null;
	}

	@Test
	public void testGet() {
		for (int i = 0; i < myArrList.size(); i++) {
			assertEquals(i * 10, myArrList.get(i).intValue());
		}
	}

	@Test
	public void testAdd() {
		myArrList.add(50);
		myArrList.add(60);
		for (int i = 0; i < myArrList.size(); i++) {
			assertEquals(i * 10, myArrList.get(i).intValue());
		}
	}

	@Test
	public void testAddIndex() {
		myArrList.add(1, 5);
		myArrList.add(3, 15);
		myArrList.add(5, 25);
		myArrList.add(7, 35);
		for (int i = 0; i < myArrList.size(); i++) {
			assertEquals(i * 5, myArrList.get(i).intValue());
		}
	}

	@Test
	public void testSize() {
		assertEquals(5, myArrList.size());
	}

	@Test
	public void testAddColl() {
		myArrList.addAll(myArrList);
		for (int i = 0; i < myArrList.size() / 2; i++) {
			assertEquals(i * 10, myArrList.get(i).intValue());
			assertEquals(i * 10, myArrList.get(i + myArrList.size() / 2)
					.intValue());
		}
	}

	@Test
	public void testAddCollIndex() {
		int j = 2;
		myArrList.addAll(j, myArrList);
		for (int i = 0; i < j; i++) {
			assertEquals(i * 10, myArrList.get(i).intValue());
		}
		for (int i = j; i < j + 5; i++) {
			assertEquals((i - j) * 10, myArrList.get(i).intValue());
		}
		for (int i = j + 5; i < myArrList.size(); i++) {
			assertEquals((i - 5) * 10, myArrList.get(i).intValue());
		}
	}

	@Test
	public void testClear() {
		myArrList.clear();
		assertEquals(0, myArrList.size());
	}

	@Test
	public void testContains() {
		for (int i = 0; i < myArrList.size(); i++) {
			assertTrue(myArrList.contains(10 * i));
			assertFalse(myArrList.contains(-i - 1));
		}
	}

	@Test
	public void testContainsAll() {
		assertTrue(myArrList.containsAll(myArrList));
		MyArrayList<Integer> buff = new MyArrayList<Integer>(1000);
		buff.add(-10);
		assertFalse(myArrList.containsAll(buff));
	}

	@Test
	public void testIndexOf() {
		myArrList.add(10);
		myArrList.add(20);
		for (int i = 0; i < 5; i++) {
			assertEquals(i, myArrList.indexOf(10 * i));
		}
	}

	@Test
	public void testLastIndexOf() {
		myArrList.add(10);
		myArrList.add(20);
		assertEquals(0, myArrList.lastIndexOf(0));
		assertEquals(5, myArrList.lastIndexOf(10));
		assertEquals(6, myArrList.lastIndexOf(20));
		assertEquals(3, myArrList.lastIndexOf(30));
		assertEquals(4, myArrList.lastIndexOf(40));
	}

	@Test
	public void testIsEmpty() {
		assertFalse(myArrList.isEmpty());
		myArrList.clear();
		assertTrue(myArrList.isEmpty());
	}

	@Test
	public void testRemoveIndex() {
		myArrList.remove(3);
		assertTrue(myArrList.size() == 4);
		for (int i = 0; i < 3; i++) {
			assertEquals(10 * i, myArrList.get(i).intValue());
		}
		assertEquals(40, myArrList.get(3).intValue());
	}

	@Test
	public void testRemoveObject() {
		myArrList.remove(new Integer(30));
		assertTrue(myArrList.size() == 4);
		for (int i = 0; i < 3; i++) {
			assertEquals(10 * i, myArrList.get(i).intValue());
		}
		assertEquals(40, myArrList.get(3).intValue());
	}

	@Test
	public void testRemoveAllColl() {
		MyArrayList<Integer> buff = new MyArrayList<Integer>(myArrList, 1000);
		myArrList.add(-10);
		myArrList.removeAll(buff);
		assertTrue(myArrList.size() == 1);
		assertEquals(myArrList.get(0).intValue(), -10);
	}

	@Test
	public void testSet() {
		for (int i = 0; i < myArrList.size(); i++) {
			myArrList.set(i, -i * 10);
		}
		for (int i = 0; i < myArrList.size(); i++) {
			assertEquals(myArrList.get(i).intValue(), -i * 10);
		}
	}

	@Test
	public void testToArray() {
		Object[] buff = myArrList.toArray();
		for (int i = 0; i < buff.length; i++) {
			assertEquals(buff[i], i * 10);
		}
	}

	@Test
	public void testTime() throws InterruptedException {
		Thread.sleep(1000);
		assertTrue(myArrList.size() == 0);
		myArrList.add(10);
		Thread.sleep(400);
		assertTrue(myArrList.size() == 1);
		myArrList.add(20);
		Thread.sleep(400);
		assertTrue(myArrList.size() == 2);
		myArrList.add(30);
		Thread.sleep(400);
		assertTrue(myArrList.size() == 2);
	}
}
