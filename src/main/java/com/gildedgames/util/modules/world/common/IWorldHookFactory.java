package com.gildedgames.util.modules.world.common;

import com.gildedgames.util.modules.world.common.world.IWorld;

public interface IWorldHookFactory<W extends IWorldHook>
{

	W create(IWorld w);

	IWorld getWorldFor(int dimId);

}
