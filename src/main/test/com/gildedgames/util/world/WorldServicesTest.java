package com.gildedgames.util.world;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.testutil.GGUtilDataSet;
import com.gildedgames.util.testutil.world.TestWorldFactory;
import com.gildedgames.util.testutil.world.TestWorldHook;
import com.gildedgames.util.testutil.world.TestWorldHookFactory;
import com.gildedgames.util.world.common.WorldHookPool;

public class WorldServicesTest
{

	private WorldServices create()
	{
		return new WorldServices(false, new TestWorldFactory());
	}

	@Test
	public void testPools()
	{
		WorldServices worldServices = this.create();
		TestWorldHookFactory factory = new TestWorldHookFactory(GGUtilDataSet.iworlds());
		WorldHookPool<TestWorldHook> pool = new WorldHookPool<TestWorldHook>(factory, "test");
		worldServices.registerWorldPool(pool);
		Assert.assertTrue("Adding or getting worldhookpools is broken", worldServices.getPools().contains(pool));
	}

	@Test
	public void testGetWrapper()
	{
		WorldServices worldServices = this.create();
		Assert.assertSame(worldServices.getWrapper(0), worldServices.getWrapper(0));
		Assert.assertNotSame(worldServices.getWrapper(0), worldServices.getWrapper(1));
	}

}
