package Task_1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class ShuffleArray {

	private static final int BYTE = 0;
	private static final int SHORT = 1;
	private static final int INTEGER = 2;
	private static final int LONG = 3;
	private static final int FLOAT = 4;
	private static final int DOUBLE = 5;
	private static final int CHARACTER = 6;
	private static final int BOOLEAN = 7;
	private static final int OBJECT = 8;

	private static int CurrentType = OBJECT;

	private static int elements = 1;
	private static int dimension = 0;
	private static Random rd;
	private static ArrayList<Integer> dimOfArray;
	private static int percent = 25;

	/**
	 * Перемішує percent відсотків елементів масиву
	 * 
	 * @param array
	 *            масив, у якому відбудеться дія
	 * @return масив після обробки
	 */
	@SuppressWarnings("unchecked")
	public static <T> T mixRandomly(T array) {
		rd = new Random();
		dimOfArray = new ArrayList<Integer>();
		calculateDimension(array);
		dimension = dimOfArray.size();
		for (int i = 0; i < dimension; i++)
			elements *= dimOfArray.get(i);
		int amountMixElements = (int) ((double) elements * percent / 100);
		HashSet<LinkedList<Integer>> buffer = new HashSet<>(amountMixElements);
		LinkedList<Integer> coordinates;
		while (buffer.size() < amountMixElements) {
			coordinates = new LinkedList<>();
			for (int j = 0; j < dimension; j++) {
				coordinates.add(rd.nextInt(dimOfArray.get(j)));
			}
			buffer.add((LinkedList<Integer>) coordinates.clone());
		}
		Iterator<LinkedList<Integer>> iter = buffer.iterator();
		coordinates = iter.next();
		LinkedList<Integer> firstCoordinates = (LinkedList<Integer>) coordinates
				.clone();
		Object buffVal = getElement(coordinates, array);
		while (iter.hasNext()) {
			buffVal = getsetElement(iter.next(), array, buffVal);
		}
		setElement(firstCoordinates, array, buffVal);
		setDefaultConfig();
		return array;
	}

	/**
	 * Знаходить кількість вимірів та визначає тип елементів, що у масиві
	 * 
	 * @param array
	 *            масив, який підлягає обробці
	 */
	private static void calculateDimension(Object array) {
		if (array instanceof Object[]) {
			Object[] ob = (Object[]) array;
			dimOfArray.add(ob.length);
			calculateDimension(ob[0]);
		} else if (array instanceof byte[]) {
			dimOfArray.add((((byte[]) array).length));
			CurrentType = BYTE;
		} else if (array instanceof short[]) {
			dimOfArray.add(((short[]) array).length);
			CurrentType = SHORT;
		} else if (array instanceof int[]) {
			dimOfArray.add(((int[]) array).length);
			CurrentType = INTEGER;
		} else if (array instanceof long[]) {
			dimOfArray.add(((long[]) array).length);
			CurrentType = LONG;
		} else if (array instanceof float[]) {
			dimOfArray.add(((float[]) array).length);
			CurrentType = FLOAT;
		} else if (array instanceof double[]) {
			dimOfArray.add(((double[]) array).length);
			CurrentType = DOUBLE;
		} else if (array instanceof char[]) {
			dimOfArray.add(((char[]) array).length);
			CurrentType = CHARACTER;
		} else if (array instanceof boolean[]) {
			dimOfArray.add(((boolean[]) array).length);
			CurrentType = BOOLEAN;
		}
	}

	/**
	 * Встановлює елемент у вказану позицію
	 * 
	 * @param coordinate
	 *            координати елементу
	 * @param array
	 *            масив у який потрібно помістити елемент
	 * @param element
	 *            елемент, який буде встановлено
	 */
	private static void setElement(LinkedList<Integer> coordinate,
			Object array, Object element) {
		if (coordinate.size() != 1) {
			Object[] arr = (Object[]) array;
			setElement(coordinate, arr[coordinate.pollFirst()], element);
		} else
			switch (CurrentType) {
			case OBJECT:
				((Object[]) array)[coordinate.pollFirst()] = element;
				break;
			case BYTE:
				((byte[]) array)[coordinate.pollFirst()] = (byte) element;
				break;
			case SHORT:
				((short[]) array)[coordinate.pollFirst()] = (short) element;
				break;
			case INTEGER:
				((int[]) array)[coordinate.pollFirst()] = (int) element;
				break;
			case LONG:
				((long[]) array)[coordinate.pollFirst()] = (long) element;
				break;
			case CHARACTER:
				((char[]) array)[coordinate.pollFirst()] = (char) element;
				break;
			case FLOAT:
				((float[]) array)[coordinate.pollFirst()] = (float) element;
				break;
			case DOUBLE:
				((double[]) array)[coordinate.pollFirst()] = (double) element;
				break;
			case BOOLEAN:
				((boolean[]) array)[coordinate.pollFirst()] = (boolean) element;
				break;
			}
	}

	/**
	 * Повертає елемент із вказаної позиції
	 * 
	 * @param coordinate
	 *            координати елементу
	 * @param array
	 *            масив у якому знаходиться елемент
	 * @return елемент, який лежав у вказаній позиції
	 */
	private static Object getElement(LinkedList<Integer> coordinate,
			Object array) {
		if (coordinate.size() != 1) {
			Object[] arr = (Object[]) array;
			return getElement(coordinate, arr[coordinate.pollFirst()]);
		} else
			switch (CurrentType) {
			case OBJECT:
				return ((Object[]) array)[coordinate.pollFirst()];
			case BYTE:
				return ((byte[]) array)[coordinate.pollFirst()];
			case SHORT:
				return ((short[]) array)[coordinate.pollFirst()];
			case INTEGER:
				return ((int[]) array)[coordinate.pollFirst()];
			case LONG:
				return ((long[]) array)[coordinate.pollFirst()];
			case CHARACTER:
				return ((char[]) array)[coordinate.pollFirst()];
			case FLOAT:
				return ((float[]) array)[coordinate.pollFirst()];
			case DOUBLE:
				return ((double[]) array)[coordinate.pollFirst()];
			case BOOLEAN:
				return ((boolean[]) array)[coordinate.pollFirst()];
			default:
				return null;
			}
	}

	/**
	 * Повертає елемент та встановлює інший елемент у вказану позицію
	 * 
	 * @param coordinate
	 *            координати елементу
	 * @param array
	 *            масив у якому відбувається зміна
	 * @param element
	 *            елемент, який буде встановлено
	 * @return елемент, який лежав у вказаній позиції
	 */
	private static Object getsetElement(LinkedList<Integer> coordinate,
			Object array, Object element) {
		if (coordinate.size() != 1) {
			Object[] arr = (Object[]) array;
			return getsetElement(coordinate, arr[coordinate.pollFirst()],
					element);
		} else
			switch (CurrentType) {
			case OBJECT:
				Object[] arr0 = (Object[]) array;
				Object buff0 = arr0[coordinate.peekFirst()];
				arr0[coordinate.pollFirst()] = element;
				return buff0;
			case BYTE:
				byte[] arr1 = (byte[]) array;
				byte buff1 = arr1[coordinate.peekFirst()];
				arr1[coordinate.pollFirst()] = (byte) element;
				return buff1;
			case SHORT:
				short[] arr2 = (short[]) array;
				short buff2 = arr2[coordinate.peekFirst()];
				arr2[coordinate.pollFirst()] = (short) element;
				return buff2;
			case INTEGER:
				int[] arr3 = (int[]) array;
				int buff3 = arr3[coordinate.peekFirst()];
				arr3[coordinate.pollFirst()] = (int) element;
				return buff3;
			case LONG:
				long[] arr4 = (long[]) array;
				long buff4 = arr4[coordinate.peekFirst()];
				arr4[coordinate.pollFirst()] = (long) element;
				return buff4;
			case CHARACTER:
				char[] arr5 = (char[]) array;
				char buff5 = arr5[coordinate.peekFirst()];
				arr5[coordinate.pollFirst()] = (char) element;
				return buff5;
			case FLOAT:
				float[] arr6 = (float[]) array;
				float buff6 = arr6[coordinate.peekFirst()];
				arr6[coordinate.pollFirst()] = (float) element;
				return buff6;
			case DOUBLE:
				double[] arr7 = (double[]) array;
				double buff7 = arr7[coordinate.peekFirst()];
				arr7[coordinate.pollFirst()] = (double) element;
				return buff7;
			case BOOLEAN:
				boolean[] arr8 = (boolean[]) array;
				boolean buff8 = arr8[coordinate.peekFirst()];
				arr8[coordinate.pollFirst()] = (boolean) element;
				return buff8;
			default:
				return null;
			}
	}

	/**
	 * Встановлює відсоток елементів, які підлягають перестановці
	 * 
	 * @param per
	 *            кількість процентів
	 */
	public static void setPercent(int per) {
		percent = per;
	}

	/**
	 * Повертає відсоток елементів, які підлягають зміні
	 * 
	 * @return кількість процентів
	 */
	public static int getPercent() {
		return percent;
	}

	/**
	 * Встановлює початкові параметри, для подальшої роботи із класом
	 */
	private static void setDefaultConfig() {
		elements = 1;
		dimension = 0;
		CurrentType = OBJECT;
		dimOfArray.clear();
	}
}
