package com.gildedgames.util.modules.instances;

import com.gildedgames.util.core.util.BlockPosDimension;

public interface IPlayerInstances
{

	Instance getInstance();

	void setInstance(Instance instance);

	BlockPosDimension outside();

	void setOutside(BlockPosDimension pos);

}
