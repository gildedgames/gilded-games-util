package com.gildedgames.util.spawning.player;

import com.gildedgames.util.player.common.IPlayerHookFactory;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class PlayerSpawningFactory implements IPlayerHookFactory<PlayerSpawning>
{

	@Override
	public PlayerSpawning create(IPlayerProfile profile, IPlayerHookPool<PlayerSpawning> pool)
	{
		return new PlayerSpawning(profile, pool);
	}

}
