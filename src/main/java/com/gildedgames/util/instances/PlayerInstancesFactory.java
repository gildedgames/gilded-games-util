package com.gildedgames.util.instances;

import com.gildedgames.util.player.common.IPlayerHookFactory;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class PlayerInstancesFactory implements IPlayerHookFactory<PlayerInstances>
{

	@Override
	public PlayerInstances create(IPlayerProfile profile, IPlayerHookPool<PlayerInstances> pool)
	{
		return new PlayerInstances(pool, profile);
	}

}
