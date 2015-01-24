package com.gildedgames.util.testutil;

import java.util.List;

import org.junit.Assert;

public class AssertUtil
{
	public static <T> void assertListEquals(List<T> expected, List<T> actual)
	{
		Assert.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
	}

}
