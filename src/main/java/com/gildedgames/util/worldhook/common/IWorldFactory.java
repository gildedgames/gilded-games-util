package com.gildedgames.util.worldhook.common;

import net.minecraft.world.World;

public interface IWorldFactory<W extends IWorldHook>
{
	
	public W create(World w);
	
}
