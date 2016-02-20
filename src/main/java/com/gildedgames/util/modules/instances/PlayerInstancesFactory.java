package com.gildedgames.util.modules.instances;

import com.gildedgames.util.modules.player.common.IPlayerHookFactory;
import com.gildedgames.util.modules.player.common.IPlayerHookPool;
import com.gildedgames.util.modules.player.common.player.IPlayerProfile;

public class PlayerInstancesFactory implements IPlayerHookFactory<PlayerInstances>
{

	@Override
	public PlayerInstances create(IPlayerProfile profile, IPlayerHookPool<PlayerInstances> pool)
	{
		return new PlayerInstances(pool, profile);
	}

}
