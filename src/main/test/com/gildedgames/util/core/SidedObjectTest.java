package com.gildedgames.util.core;

import org.junit.Assert;
import org.junit.Test;

public class SidedObjectTest
{

	@Test
	public void test()
	{
		SidedObject<Integer> sidedObject = new SidedObject<Integer>(0, 5);
		Assert.assertEquals(sidedObject.client(), new Integer(0));
		Assert.assertEquals(sidedObject.server(), new Integer(5));
	}
}
