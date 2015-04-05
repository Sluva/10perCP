package Task_2;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class MyArrayList<T> extends AbstractList<T> {
	private Object[] array;
	private int size = 0;
	private long time;
	long[] timeOfCreate;

	public MyArrayList(long time) {
		array = new Object[10];
		timeOfCreate = new long[10];
		this.time = time;
	}

	public MyArrayList(int capacity, long time) {
		if (capacity < 0)
			new Exception("capacity should be no negative");
		array = new Object[capacity];
		timeOfCreate = new long[capacity];
		this.time = time;
	}

	public MyArrayList(Collection<? extends T> collection, long time) {
		array = collection.toArray();
		timeOfCreate = new long[array.length];
		Arrays.fill(timeOfCreate, System.currentTimeMillis());
		size = collection.size();
		this.time = time;
	}

	private void ensureCapacity(int capacity) {
		if (array.length < capacity) {
			array = Arrays.copyOf(array, capacity + 10);
			timeOfCreate = Arrays.copyOf(timeOfCreate, capacity + 10);
		}
	}

	@Override
	public boolean add(T e) {
		ensureCapacity(size + 1);
		array[size] = e;
		timeOfCreate[size] = System.currentTimeMillis();
		size++;
		return true;
	}

	@Override
	public void add(int index, T element) {
		removeOld();
		ensureCapacity(size + 1);
		System.arraycopy(array, index, array, index + 1, size - index);
		System.arraycopy(timeOfCreate, index, timeOfCreate, index + 1, size
				- index);
		array[index] = element;
		timeOfCreate[index] = System.currentTimeMillis();
		size++;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		ensureCapacity(size + c.size());
		System.arraycopy(c.toArray(), 0, array, size, c.size());
		Arrays.fill(timeOfCreate, size, size + c.size(),
				System.currentTimeMillis());
		size += c.size();
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		removeOld();
		ensureCapacity(size + c.size());
		System.arraycopy(array, index, array, c.size() + index, size - index);
		System.arraycopy(timeOfCreate, index, timeOfCreate, c.size(), size
				- index);
		System.arraycopy(c.toArray(), 0, array, index, c.size());
		Arrays.fill(timeOfCreate, index, index + c.size(),
				System.currentTimeMillis());
		size += c.size();
		return true;
	}

	@Override
	public void clear() {
		array = new Object[array.length];
		timeOfCreate = new long[array.length];
		size = 0;
	}

	@Override
	public boolean contains(Object o) {
		removeOld();
		return indexOf(o) >= 0;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		removeOld();
		Iterator<?> iterator = c.iterator();
		while (iterator.hasNext())
			if (indexOf(iterator.next()) == -1)
				return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int index) {
		removeOld();
		if (index < 0 || index >= size)
			new Exception("index should be: 0 <= index < size");
		return (T) array[index];
	}

	@Override
	public int indexOf(Object o) {
		removeOld();
		for (int i = 0; i < size; i++)
			if (o.equals(array[i]))
				return i;
		return -1;
	}

	@Override
	public boolean isEmpty() {
		removeOld();
		return size == 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		removeOld();
		for (int i = size - 1; i >= 0; i--)
			if (o.equals(array[i]))
				return i;
		return -1;
	}

	@Override
	public boolean remove(Object o) {
		int i = indexOf(o);
		if (i == -1)
			return false;
		System.arraycopy(array, i + 1, array, i, size - i - 1);
		array[--size] = null;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T remove(int index) {
		removeOld();
		if (index >= size || size < 0)
			new Exception("index should be: 0 <= index < size");
		T value = (T) array[index];
		System.arraycopy(array, index + 1, array, index, size - index - 1);
		array[--size] = null;
		return value;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Iterator<?> iterator = c.iterator();
		while (iterator.hasNext()) {
			Object ob = iterator.next();
			for (int i = 0; i < size; i++) {
				if (ob.equals(array[i])) {
					System.arraycopy(array, i + 1, array, i, size - i - 1);
					array[--size] = null;
					break;
				}
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T set(int index, T element) {
		removeOld();
		T value = (T) array[index];
		array[index] = element;
		timeOfCreate[index] = System.currentTimeMillis();
		return value;
	}

	@Override
	public int size() {
		removeOld();
		return size;
	}

	@Override
	public Object[] toArray() {
		removeOld();
		return Arrays.copyOf(array, size);
	}

	public void trimToSize() {
		removeOld();
		array = Arrays.copyOf(array, size);
	}

	private void removeOld() {
		long curTime = System.currentTimeMillis();
		int amount = 0;
		for (int i = 0; i < size; i++) {
			if (curTime - timeOfCreate[i] >= time) {
				System.arraycopy(array, i + 1, array, i, size - i);
				amount++;
			}
		}
		size -= amount;
	}
}
