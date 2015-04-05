package Task_2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyMap<K, V> implements Map<K, V> {

	HashSet<K> set = new HashSet<>();
	private MapEntry header = new MapEntry(null, null, null, Long.MAX_VALUE);
	private int size = 0;
	private long timeOfLife;

	public MyMap(long timeOfLife) {
		this.timeOfLife = timeOfLife;
		header.next = header;
	}

	@Override
	public void clear() {
		MapEntry e = header.next;
		while (e != header) {
			MapEntry next = e.next;
			e.next = null;
			e.key = null;
			e.value = null;
			e = next;
		}
		set.clear();
		header.next = header;
		size = 0;
	}

	@Override
	public boolean containsKey(Object key) {
		removeOld();
		return set.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		removeOld();
		MapEntry e = header.next;
		while (e != header) {
			if (e.value.equals(value))
				return true;
			e = e.next;
		}
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		removeOld();
		MapEntry e = header.next;
		HashSet<java.util.Map.Entry<K, V>> newSet = new HashSet<>();
		while (e != header) {
			newSet.add(e);
		}
		return newSet;
	}

	@Override
	public V get(Object key) {
		MapEntry e = header.next;
		long curTime = System.currentTimeMillis();
		while (e != header) {
			if (e.key.equals(key))
				if (curTime - e.timeOfCreate > timeOfLife)
					return null;
				else
					return e.value;
			e = e.next;
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		removeOld();
		return size == 0;
	}

	@Override
	public Set<K> keySet() {
		removeOld();
		return set;
	}

	@Override
	public V put(K key, V value) {
		if (containsKey(key)) {
			MapEntry e = header.next;
			while (e != header) {
				if (e.key.equals(key)) {
					V val = e.value;
					e.value = value;
					return val;
				}
				e = e.next;
			}
		} else {
			header.next = new MapEntry(key, value, header.next,
					System.currentTimeMillis());
			size++;
		}
		return value;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		Iterator<? extends K> ite = m.keySet().iterator();
		K newKey;
		while (ite.hasNext()) {
			newKey = ite.next();
			put(newKey, m.get(newKey));
		}
	}

	@Override
	public V remove(Object key) {
		if (containsKey(key)) {
			MapEntry e = header.next;
			MapEntry old = header;
			while (e != header) {
				if (e.key.equals(key)) {
					V val = e.value;
					old.next = e.next;
					set.remove(e.key);
					e.next = null;
					e.key = null;
					e.value = null;
					e = old.next;
					size--;
					return val;
				}
				old = e;
				e = e.next;
			}
		}
		return null;
	}

	@Override
	public int size() {
		removeOld();
		return size;
	}

	@Override
	public Collection<V> values() {
		removeOld();
		MapEntry e = header.next;
		Collection<V> newColl = new ArrayList<>();
		while (e != header) {
			newColl.add(e.value);
		}
		return newColl;
	}

	private void removeOld() {
		MapEntry old = header;
		MapEntry newEntry = header.next;
		long curTime = System.currentTimeMillis();
		while (newEntry != header)
			if (curTime - newEntry.timeOfCreate > timeOfLife) {
				old.next = newEntry.next;
				set.remove(newEntry.key);
				newEntry.next = null;
				newEntry.key = null;
				newEntry.value = null;
				newEntry = old.next;
				size--;
			} else {
				old = newEntry;
				newEntry = newEntry.next;
			}
	}

	private class MapEntry implements Entry<K, V> {

		MapEntry next;
		K key;
		V value;
		long timeOfCreate;

		public MapEntry(K key, V value, MapEntry next, long timeOfCreate) {
			this.key = key;
			this.value = value;
			this.next = next;
			this.timeOfCreate = timeOfCreate;
			set.add(key);
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}
	}
}
