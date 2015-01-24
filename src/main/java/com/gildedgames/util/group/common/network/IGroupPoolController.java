package com.gildedgames.util.group.common.network;

import com.gildedgames.util.group.common.IGroupPool;

public interface IGroupPoolController extends IGroupPool
{

	void setTargetPool(IGroupPool targetPool);
	
}
