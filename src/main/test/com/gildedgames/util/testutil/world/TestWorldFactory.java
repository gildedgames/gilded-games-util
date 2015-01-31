package com.gildedgames.util.testutil.world;

import com.gildedgames.util.world.common.world.IWorldFactory;

public class TestWorldFactory implements IWorldFactory<TestWorld>
{

	@Override
	public TestWorld create(int dimId, boolean isRemote)
	{
		return new TestWorld(dimId);
	}

}
