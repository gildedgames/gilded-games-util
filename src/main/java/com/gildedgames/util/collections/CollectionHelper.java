package com.gildedgames.util.collections;

import java.util.Collection;
import java.util.Random;

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
}
