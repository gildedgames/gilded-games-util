package com.gildedgames.util.group.common;

import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public interface IGroupFactory<P extends IGroup>
{
	
	P create(IGroupPool<P> parentPool);

}
