package com.gildedgames.util.worldhook.common;

import com.gildedgames.util.worldhook.common.world.IWorld;

public interface IWorldHookFactory<W extends IWorldHook>
{

	public W create(IWorld w);

}
