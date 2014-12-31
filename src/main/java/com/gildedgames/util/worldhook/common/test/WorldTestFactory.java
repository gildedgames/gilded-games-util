package com.gildedgames.util.worldhook.common.test;

import net.minecraft.world.World;

import com.gildedgames.util.worldhook.common.IWorldFactory;

public class WorldTestFactory implements IWorldFactory<WorldTest>
{

	@Override
	public WorldTest create(World w)
	{
		return new WorldTest(w);
	}

}
