package com.gildedgames.util.world.common;

import com.gildedgames.util.world.common.world.IWorld;

public interface IWorldHookFactory<W extends IWorldHook>
{

	public W create(IWorld w);

}
