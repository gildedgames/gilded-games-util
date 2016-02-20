package com.gildedgames.util.modules.player.common;

import com.gildedgames.util.modules.player.common.player.IPlayerHook;
import com.gildedgames.util.modules.player.common.player.IPlayerProfile;

public interface IPlayerHookFactory<P extends IPlayerHook>
{
	
	P create(IPlayerProfile profile, IPlayerHookPool<P> pool);

}
