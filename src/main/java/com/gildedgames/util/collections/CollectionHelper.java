package com.gildedgames.util.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.google.common.base.Function;

public class CollectionHelper
{

	/**
	 * Choose a non-uniformly distributed element from the
	 * collection given. The chance for each element should
	 * be given by the IChanceExposer.
	 * When the collection is empty, or all its elements have
	 * chance 0, it returns null. 
	 */
	public static <T> T chooseDistributed(Collection<T> iter, Random random, IChanceExposer<T> exposer)
	{
		if (iter.size() == 0)
		{
			return null;
		}
		float total = 0;
		for (T element : iter)
		{
			total += exposer.getChance(element);
		}

		final float randomValue = random.nextFloat() * total;
		float chanceSum = 0.0F;

		for (final T element : iter)
		{
			float chance = exposer.getChance(element);
			chanceSum += chance;
			if (randomValue <= chanceSum)
			{
				return element;
			}
		}
		return null;
	}

	public static int getNextFreeId(Collection<Integer> collection)
	{
		return getNextFreeIdFrom(collection, new Function<Integer, Integer>()
		{
			@Override
			public Integer apply(Integer input)
			{
				return input;
			}
		}, 0);
	}

	public static <T> int getNextFreeIdFrom(Collection<T> collection, Function<T, Integer> getId, int min)
	{
		int expecting = min;
		final Set<Integer> foundNumbers = new HashSet<Integer>(collection.size());
		for (final T data : collection)
		{
			foundNumbers.add(getId.apply(data));
			while (foundNumbers.contains(expecting))
			{
				expecting++;
			}
		}
		return expecting;
	}

	public static <T> int max(Collection<T> collection, Function<T, Integer> size)
	{
		int max = Integer.MIN_VALUE;
		for (T el : collection)
		{
			max = Math.max(max, size.apply(el));
		}
		return max;
	}

	public static <T> int min(Collection<T> collection, Function<T, Integer> size)
	{
		int min = Integer.MAX_VALUE;
		for (T el : collection)
		{
			min = Math.min(min, size.apply(el));
		}
		return min;
	}

	public static <K, V> K getKey(Map<K, V> map, V value)
	{
		for (Entry<K, V> entry : map.entrySet())
		{
			if (entry.getValue().equals(value))
			{
				return entry.getKey();
			}
		}
		return null;
	}
}
