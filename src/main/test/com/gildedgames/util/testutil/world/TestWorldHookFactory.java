package com.gildedgames.util.testutil.world;

import java.util.List;

import com.gildedgames.util.world.common.IWorldHookFactory;
import com.gildedgames.util.world.common.world.IWorld;

public class TestWorldHookFactory implements IWorldHookFactory<TestWorldHook>
{

	List<IWorld> worlds;

	private static int id = 0;

	public TestWorldHookFactory(List<IWorld> worlds)
	{
		this.worlds = worlds;
	}

	@Override
	public TestWorldHook create(IWorld w)
	{
		return new TestWorldHook(w, id++);
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
