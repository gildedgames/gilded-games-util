package com.gildedgames.util.testutil;

import java.util.List;

import com.gildedgames.util.world.common.IWorldHookFactory;
import com.gildedgames.util.world.common.world.IWorld;

public class TestWorldHookFactory implements IWorldHookFactory<TestWorldHook>
{

	List<IWorld> worlds;

	public TestWorldHookFactory(List<IWorld> worlds)
	{
		this.worlds = worlds;
	}

	@Override
	public TestWorldHook create(IWorld w)
	{
		return new TestWorldHook(w);
	}

	@Override
	public IWorld getWorldFor(int dimId)
	{
		for (IWorld world : this.worlds)
		{
			if (world.getDimensionID() == dimId)
			{
				return world;
			}
		}
		return null;
	}

}
