package com.gildedgames.util.worldhook.common;

import com.gildedgames.util.worldhook.common.world.IWorld;

public interface IWorldFactory<W extends IWorldHook>
{

	public W create(IWorld w);

}
