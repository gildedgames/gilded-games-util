package com.gildedgames.util.worldhook.common.test;

import com.gildedgames.util.worldhook.common.IWorldFactory;
import com.gildedgames.util.worldhook.common.world.IWorld;

public class WorldTestFactory implements IWorldFactory<WorldTest>
{

	@Override
	public WorldTest create(IWorld w)
	{
		return new WorldTest(w);
	}

}
