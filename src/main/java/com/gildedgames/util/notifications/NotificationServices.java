package com.gildedgames.util.notifications;

import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.notifications.core.INotification;
import com.gildedgames.util.notifications.core.NotificationDispatcher;
import com.gildedgames.util.notifications.core.NotificationDispatcherServer;
import com.gildedgames.util.notifications.player.PlayerNotificationFactory;
import com.gildedgames.util.notifications.player.PlayerNotifications;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerHookPool;

public class NotificationServices
{
	private Side side;

	private IPlayerHookPool<PlayerNotifications> players;

	private NotificationDispatcher dispatcher;

	protected NotificationServices(Side side)
	{
		this.side = side;
	}

	public IPlayerHookPool<PlayerNotifications> getPlayers()
	{
		if (this.players == null)
		{
			this.players = new PlayerHookPool<PlayerNotifications>("group", new PlayerNotificationFactory(), this.side);
		}

		return this.players;
	}

	public void queueNotificationForDisplay(INotification notification)
	{

	}

	public void onRenderOverlay()
	{

	}

	public NotificationDispatcher getDispatcher()
	{
		if (this.dispatcher == null)
		{
			this.dispatcher = new NotificationDispatcherServer();
		}

		return this.dispatcher;
	}
}
