package com.gildedgames.util.modules.notifications.common.player;

import com.gildedgames.util.modules.player.common.IPlayerHookFactory;
import com.gildedgames.util.modules.player.common.IPlayerHookPool;
import com.gildedgames.util.modules.player.common.player.IPlayerProfile;

public class PlayerNotificationFactory implements IPlayerHookFactory<PlayerNotification>
{

	@Override
	public PlayerNotification create(IPlayerProfile profile, IPlayerHookPool<PlayerNotification> pool)
	{
		return new PlayerNotification(profile, pool);
	}

}
