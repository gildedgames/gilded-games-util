package com.gildedgames.util.core;

import org.junit.Assert;
import org.junit.Test;

public class UtilServicesTest
{

	@Test
	public void test()
	{
		UtilServices services = new UtilServices();
		Assert.assertSame(services.getIO(), services.getIO());
	}

}
