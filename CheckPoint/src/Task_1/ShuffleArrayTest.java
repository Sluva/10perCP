package Task_1;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShuffleArrayTest {
	Random random;
	int[][][] test1_1;
	int[][][] test1_2;
	boolean[] test2_1;
	boolean[] test2_2;
	Object[] test3_1;
	Object[] test3_2;
	String[][][][][] test4_1;
	String[][][][][] test4_2;
	Double[][] test5_1;
	Double[][] test5_2;
	final int a = 40;
	final int b = 30;
	final int c = 20;
	final int d = 15;
	final int e = 10;

	@Before
	public void setUp() throws Exception {
		random = new Random();
		test1_1 = new int[a][b][c];
		test1_2 = new int[a][b][c];
		test2_1 = new boolean[a];
		test2_2 = new boolean[a];
		test3_1 = new Object[a];
		test3_2 = new Object[a];
		test4_1 = new String[a][b][c][d][e];
		test4_2 = new String[a][b][c][d][e];
		test5_1 = new Double[a][b];
		test5_2 = new Double[a][b];

		for (int i = 0; i < a; i++) {
			test2_1[i] = test2_2[i] = random.nextBoolean();
			test3_1[i] = test3_2[i] = new Object();
			for (int j = 0; j < b; j++) {
				test5_1[i][j] = test5_2[i][j] = new Double(1);
				for (int k = 0; k < c; k++) {
					test1_1[i][j][k] = test1_2[i][j][k] = random.nextInt(100);
					for (int x = 0; x < d; x++) {
						for (int y = 0; y < e; y++) {
							test4_1[i][j][k][x][y] = test4_2[i][j][k][x][y] = ""
									+ i + "_" + j + "_" + k + "_" + x + "_" + y;
						}
					}
				}
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		test1_1 = null;
		test1_2 = null;
		test2_1 = null;
		test2_2 = null;
		test3_1 = null;
		test3_2 = null;
		test4_1 = null;
		test4_2 = null;
		test5_1 = null;
		test5_2 = null;
	}

	@Test
	public void test() {
		int test1 = 0;
		int test2 = 0;
		int test3 = 0;
		int test4 = 0;
		int test5_I = 0;
		int test5_II = 0;
		ShuffleArray.mixRandomly(test1_1);
		ShuffleArray.mixRandomly(test2_1);
		ShuffleArray.mixRandomly(test3_1);
		ShuffleArray.mixRandomly(test4_1);
		ShuffleArray.mixRandomly(test5_1);
		for (int i = 0; i < a; i++) {
			if (test2_1[i] != test2_2[i])
				test2++;
			if (test3_1[i] != test3_2[i])
				test3++;
			test3_1[i] = test3_2[i] = new Object();
			for (int j = 0; j < b; j++) {
				if (test5_1[i][j] != test5_2[i][j])
					test5_I++;
				if (test5_1[i][j].equals(test5_2[i][j]))
					test5_II++;
				for (int k = 0; k < c; k++) {
					if (test1_1[i][j][k] != test1_2[i][j][k])
						test1++;
					for (int x = 0; x < d; x++) {
						for (int y = 0; y < e; y++) {
							if (test4_1[i][j][k][x][y] != test4_2[i][j][k][x][y])
								test4++;
						}
					}
				}
			}
		}
		double minPercent = ShuffleArray.getPercent() - 1;
		double maxPercent = ShuffleArray.getPercent() + 1;
		double shuffledPercent = 100. * test1 / (a * b * c);
		assertTrue(shuffledPercent < maxPercent && shuffledPercent > minPercent);
		shuffledPercent = 100. * test2 / a;
		// False - тому що boolean обмежений 2 можливими значеннями, що
		// призводить до обміну однакових елементів, які неможливо перевірити
		// тестом (резульат < 25%)
		assertFalse(shuffledPercent < maxPercent
				&& shuffledPercent > minPercent);
		shuffledPercent = 100. * test3 / a;
		assertTrue(shuffledPercent < maxPercent && shuffledPercent > minPercent);
		shuffledPercent = 100. * test4 / (a * b * c * d * e);
		assertTrue(shuffledPercent < maxPercent && shuffledPercent > minPercent);
		shuffledPercent = 100. * test5_I / (a * b);
		// Выводит 25% хотя елементы идентичны
		assertTrue(shuffledPercent < maxPercent && shuffledPercent > minPercent);
		shuffledPercent = 100. * test5_II / (a * b);
		// Выводит еденицу так как елементы идентичны
		assertTrue(shuffledPercent > 0.99);
	}
}
