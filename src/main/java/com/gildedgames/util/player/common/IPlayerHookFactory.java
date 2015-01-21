package com.gildedgames.util.player.common;

import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public interface IPlayerHookFactory<P extends IPlayerHook>
{
	P create(IPlayerProfile profile, IPlayerHookPool<P> pool);

}
