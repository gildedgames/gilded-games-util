package com.gildedgames.util.universe.common.player;

import com.gildedgames.util.player.common.IPlayerHookFactory;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class PlayerUniverseFactory implements IPlayerHookFactory<PlayerUniverse>
{

	@Override
	public PlayerUniverse create(IPlayerProfile profile, IPlayerHookPool<PlayerUniverse> pool)
	{
		return new PlayerUniverse(pool, profile);
	}

}
