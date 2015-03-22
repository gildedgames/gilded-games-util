package com.gildedgames.util.notifications.player;

import com.gildedgames.util.player.common.IPlayerHookFactory;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class PlayerNotificationFactory implements IPlayerHookFactory<PlayerNotifications>
{

	@Override
	public PlayerNotifications create(IPlayerProfile profile, IPlayerHookPool<PlayerNotifications> pool)
	{
		return new PlayerNotifications(profile, pool);
	}

}
