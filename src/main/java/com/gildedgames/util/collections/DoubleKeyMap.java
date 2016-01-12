package com.gildedgames.util.collections;

import java.util.HashMap;
import java.util.Map;

/**
 * A map with two keys where order doesn't matter.
 * So, map.get(key1, key2) == map.get(key2, key1)
 * @author Emile
 */
public class DoubleKeyMap<K, V>
{
	private Map<Tuple<K, K>, V> map;

	public DoubleKeyMap()
	{
		this.map = new HashMap<>();
	}

	public DoubleKeyMap(int capacity)
	{
		this.map = new HashMap<>(capacity);
	}

	public V put(K key1, K key2, V value)
	{
		V old = this.remove(key1, key2);
		this.map.put(new Tuple<>(key1, key2), value);
		return old;
	}

	public V get(K key1, K key2)
	{
		Tuple<K, K> t1 = new Tuple<>(key1, key2);
		Tuple<K, K> t2 = new Tuple<>(key2, key1);
		if (this.map.containsKey(t1))
		{
			return this.map.get(t1);
		}
		return this.map.get(t2);
	}

	public V remove(K key1, K key2)
	{
		Tuple<K, K> t1 = new Tuple<>(key1, key2);
		Tuple<K, K> t2 = new Tuple<>(key2, key1);
		if (this.map.containsKey(t1))
		{
			return this.map.remove(t1);
		}
		return this.map.remove(t2);
	}

	public boolean containsKey(K key1, K key2)
	{
		Tuple<K, K> t1 = new Tuple<>(key1, key2);
		Tuple<K, K> t2 = new Tuple<>(key2, key1);
		return this.map.containsKey(t1) || this.map.containsKey(t2);
	}
}
