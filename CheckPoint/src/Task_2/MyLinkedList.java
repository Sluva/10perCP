package Task_2;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.ListIterator;

public class MyLinkedList<T> extends AbstractSequentialList<T> implements
		Deque<T> {
	private Entry header = new Entry(null, null, null, Long.MAX_VALUE);
	private int size = 0;
	private long timeOfLife;

	public MyLinkedList(long time) {
		header.next = header;
		header.prev = header;
		this.timeOfLife = time;
	}

	public MyLinkedList(Collection<? extends T> collection, long time) {
		header.next = header;
		header.prev = header;
		this.timeOfLife = time;
		addAll(collection);
	}

	private class Entry {
		T element;
		Entry next;
		Entry prev;
		long createTime;

		Entry(T element, Entry next, Entry prev, long createTime) {
			this.element = element;
			this.next = next;
			this.prev = prev;
			this.createTime = createTime;
		}
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		for (Entry e = getNextLivingEntry(header.next); e != header; e = getNextLivingEntry(e.next))
			;
		return new ListItr(index);
	}

	private class ListItr implements ListIterator<T> {
		private Entry lastReturned = header;
		private Entry next;
		private int nextIndex;

		ListItr(int index) {
			if (index < (size >> 1)) {
				next = header.next;
				for (nextIndex = 0; nextIndex < index; nextIndex++)
					next = next.next;
			} else {
				next = header;
				for (nextIndex = size; nextIndex > index; nextIndex--)
					next = next.prev;
			}
		}

		@Override
		public boolean hasNext() {
			return nextIndex != size;
		}

		@Override
		public T next() {
			lastReturned = next;
			next = next.next;
			nextIndex++;
			return lastReturned.element;
		}

		@Override
		public boolean hasPrevious() {
			return nextIndex != 0;
		}

		@Override
		public T previous() {
			lastReturned = next = next.prev;
			nextIndex--;
			return lastReturned.element;
		}

		@Override
		public int nextIndex() {
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			return nextIndex - 1;
		}

		@Override
		public void remove() {
			Entry lastNext = lastReturned.next;
			MyLinkedList.this.remove(lastReturned);
			if (next == lastReturned)
				next = lastNext;
			else
				nextIndex--;
			lastReturned = header;
		}

		@Override
		public void set(T e) {
			lastReturned.element = e;
		}

		@Override
		public void add(T e) {
			lastReturned = header;
			addEntry(e, next);
			nextIndex++;
		}
	}

	@Override
	public int size() {
		for (Entry e = getNextLivingEntry(header.next); e != header; e = getNextLivingEntry(e.next))
			;
		return size;
	}

	private Entry getEntry(int index) {
		Entry e = header.next;
		for (int i = 0; i < index; i++) {
			e = getNextLivingEntry(e);
			e = e.next;
		}
		return e;
	}

	private void addEntry(T element, Entry index) {
		Entry newEntry = new Entry(element, index, index.prev,
				System.currentTimeMillis());
		newEntry.prev.next = newEntry;
		newEntry.next.prev = newEntry;
		size++;
	}

	@Override
	public boolean add(T element) {
		addEntry(element, header);
		return true;
	}

	@Override
	public void addFirst(T element) {
		addEntry(element, header.next);
	}

	@Override
	public void addLast(T element) {
		addEntry(element, header);
	}

	@Override
	public void add(int index, T element) {
		int i = 0;
		Entry e;
		for (e = getNextLivingEntry(header.next); e != header && i != index; e = getNextLivingEntry(e.next)) {
			i++;
		}
		if (e != header)
			addEntry(element, e);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		Object[] ob = c.toArray();
		for (int i = 0; i < ob.length; i++) {
			add(index + i, (T) ob[i]);
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return addAll(size, c);
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		if (o == null) {
			for (Entry e = getNextLivingEntry(header.next); e != header; e = getNextLivingEntry(e.next)) {
				if (e.element == null)
					return index;
				index++;
			}
		} else {
			for (Entry e = getNextLivingEntry(header.next); e != header; e = getNextLivingEntry(e.next)) {
				if (o.equals(e.element))
					return index;
				index++;
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		int index = 0;
		Entry e;
		if (o == null) {
			for (e = getPreviousLivingEntry(header.prev); e != header; e = getPreviousLivingEntry(e.prev))
				if (e.element == null)
					break;
		} else
			for (e = getPreviousLivingEntry(header.prev); e != header; e = getPreviousLivingEntry(e.prev))
				if (o.equals(e.element))
					break;
		Entry ei = header.next;
		if (e != header)
			for (ei = getNextLivingEntry(header.next); ei != header; ei = getNextLivingEntry(ei.next)) {
				if (ei != e)
					index++;
				else
					return index;
			}
		return -1;
	}

	private Entry getNextLivingEntry(Entry entry) {
		long curTime = System.currentTimeMillis();
		while (curTime - entry.createTime >= timeOfLife && entry != header) {
			entry = entry.next;
			remove(entry.prev);
		}
		return entry;
	}

	private Entry getPreviousLivingEntry(Entry entry) {
		long curTime = System.currentTimeMillis();
		while (curTime - entry.createTime >= timeOfLife && entry != header) {
			entry = entry.prev;
			remove(entry.next);
		}
		return entry;
	}

	@Override
	public T set(int index, T element) {
		Entry e = getEntry(index);
		T value = e.element;
		e.element = element;
		return value;
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) != -1;
	}

	@Override
	public Iterator<T> descendingIterator() {
		for (Entry e = getNextLivingEntry(header.next); e != header; e = getNextLivingEntry(e.next))
			;
		return new DescendingIterator();
	}

	private class DescendingIterator implements Iterator<T> {
		final ListItr itr = new ListItr(size());

		public boolean hasNext() {
			return itr.hasPrevious();
		}

		public T next() {
			return itr.previous();
		}

		public void remove() {
			itr.remove();
		}
	}

	@Override
	public T element() {
		return getFirst();
	}

	@Override
	public T getFirst() {
		Entry e = header.next;
		long curTime = System.currentTimeMillis();
		while (curTime - e.createTime >= timeOfLife || e != header) {
			e = e.next;
			remove(e.prev);
		}
		return e.element;
	}

	@Override
	public T getLast() {
		Entry e = header.prev;
		long curTime = System.currentTimeMillis();
		while (curTime - e.createTime >= timeOfLife || e != header) {
			e = e.prev;
			remove(e.next);
		}
		return e.element;
	}

	@Override
	public T get(int index) {
		return getEntry(index).element;
	}

	@Override
	public boolean offer(T e) {
		return add(e);
	}

	@Override
	public boolean offerFirst(T e) {
		addFirst(e);
		return true;
	}

	@Override
	public boolean offerLast(T e) {
		addLast(e);
		return true;
	}

	@Override
	public T peek() {
		if (size == 0)
			return null;
		return getFirst();
	}

	@Override
	public T peekFirst() {
		if (size == 0)
			return null;
		return getFirst();
	}

	@Override
	public T peekLast() {
		if (size == 0)
			return null;
		return getLast();
	}

	@Override
	public T poll() {
		if (size == 0)
			return null;
		return removeFirst();
	}

	@Override
	public T pollFirst() {
		if (size == 0)
			return null;
		return removeFirst();
	}

	@Override
	public T pollLast() {
		if (size == 0)
			return null;
		return removeLast();
	}

	@Override
	public T pop() {
		return removeFirst();
	}

	@Override
	public void push(T e) {
		addFirst(e);
	}

	@Override
	public T remove() {
		return removeFirst();
	}

	@Override
	public T removeFirst() {
		Entry e = header.next;
		long curTime = System.currentTimeMillis();
		while (curTime - e.createTime >= timeOfLife || e != header) {
			e = e.next;
			remove(e.prev);
		}
		return remove(e);
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		return remove(o);
	}

	@Override
	public T removeLast() {
		Entry e = header.prev;
		long curTime = System.currentTimeMillis();
		while (curTime - e.createTime >= timeOfLife || e != header) {
			e = e.prev;
			remove(e.next);
		}
		return remove(e);
	}

	@Override
	public T remove(int index) {
		return remove(getEntry(index));
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		if (o == null) {
			for (Entry e = getNextLivingEntry(header.prev); e != header; e = getNextLivingEntry(e.prev)) {
				if (e.element == null) {
					remove(e);
					return true;
				}
			}
		} else {
			for (Entry e = getNextLivingEntry(header.prev); e != header; e = getNextLivingEntry(e.prev)) {
				if (o.equals(e.element)) {
					remove(e);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		if (o == null) {
			for (Entry e = getNextLivingEntry(header.next); e != header; e = getNextLivingEntry(e.next)) {
				if (e.element == null) {
					remove(e);
					return true;
				}
			}
		} else {
			for (Entry e = getNextLivingEntry(header.next); e != header; e = getNextLivingEntry(e.next)) {
				if (o.equals(e.element)) {
					remove(e);
					return true;
				}
			}
		}
		return false;
	}

	private T remove(Entry e) {
		T result = e.element;
		e.prev.next = e.next;
		e.next.prev = e.prev;
		e.next = e.prev = null;
		e.element = null;
		size--;
		return result;
	}

	@Override
	public void clear() {
		Entry e = header.next;
		while (e != header) {
			Entry next = e.next;
			e.next = e.prev = null;
			e.element = null;
			e = next;
		}
		header.next = header.prev = header;
		size = 0;
	}

	@Override
	public Object[] toArray() {
		for (Entry e = getNextLivingEntry(header.next); e != header; e = getNextLivingEntry(e.next))
			;
		Object[] result = new Object[size];
		int i = 0;
		for (Entry e = header.next; e != header; e = e.next)
			result[i++] = e.element;
		return result;
	}
}
